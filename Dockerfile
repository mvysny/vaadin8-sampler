# Allows you to run this app easily as a docker container.
# See README.md for more details.
#
# 1. Build the image with: docker build -t test/vaadin8-sampler:latest .
# 2. Run the image with: docker run --rm -ti -p8080:8080 -m256m test/vaadin8-sampler
#
# Uses Docker Multi-stage builds: https://docs.docker.com/build/building/multi-stage/

# The "Build" stage. Copies the entire project into the container, into the /app/ folder, and builds it.
FROM eclipse-temurin:11 AS builder
COPY . /app/
WORKDIR /app/
RUN --mount=type=cache,target=/root/.m2 ./mvnw -C clean package
WORKDIR /app/target/
RUN mkdir app && tar xvf *.tar -C app/
# At this point, we have the app (executable bash scrip plus a bunch of jars) in the
# /app/target/app/ folder.

# The "Run" stage. Start with a clean image, and copy over just the app itself, omitting gradle, npm and any intermediate build files.
FROM eclipse-temurin:11
COPY --from=builder /app/target/app /app/
WORKDIR /app/
EXPOSE 8080
ENTRYPOINT ["./bin/app"]
