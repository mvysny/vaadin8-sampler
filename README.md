# Vaadin 8 Sampler

Template for a simple Vaadin application that only requires a Servlet 3.0 container to run.
Ideal for testing Vaadin 8 component functionality on the actual framework.

[Live Demo at v-herd.eu](https://v-herd.eu/vaadin8-sampler).

This app demoes all the free core components offered by the Vaadin 8 framework,
including the Vaadin 7 compatibility package. Every panel demoes one component and shows
two parts:

* The component itself, demoed as-is
* The left pane allows you to control the behavior of the component: it should contain
  controls for all aspects of the component:
  * The behavior of the component, including the caption of the component, tooltip,
    enablement, readonly-ness for fields, etc
  * The themes and styles

You can use the idea of the "Sampler" to demo the custom components of your own app
the same way - you would demo all of your custom layouts, components and fields:
their behavior, colors, styles. The idea is to have a
go-to off-the-shelf recipes and best practices, for new developers to follow.

## Workflow

To compile the entire project, run `mvn -C clean package`.

To run the application, you can run the `Main.main()` method from your IDE, then open http://localhost:8080/ .
Alternatively run `mvn exec:java` to start the app.

To produce a deployable production mode zip:
- change productionMode to true in the servlet class configuration (nested in the UI class)
- run "mvn clean package"
- Unzip `target/*.zip` and run the `bin/app` script.

### Client-Side compilation

The generated maven project is using an automatically generated widgetset by default. 
When you add a dependency that needs client-side compilation, the maven plugin will 
automatically generate it for you. Your own client-side customizations can be added into
package "client".

Debugging client side code
  - run "mvn vaadin:run-codeserver" on a separate console while the application is running
  - activate Super Dev Mode in the debug window of the application

### Developing a theme using the runtime compiler

When developing the theme, Vaadin can be configured to compile the SASS based
theme at runtime in the server. This way you can just modify the scss files in
your IDE and reload the browser to see changes.

To use the runtime compilation, open pom.xml and comment out the compile-theme 
goal from vaadin-maven-plugin configuration. To remove a possibly existing 
pre-compiled theme, run "mvn clean package" once.

When using the runtime compiler, running the application in the "run" mode 
(rather than in "debug" mode) can speed up consecutive theme compilations
significantly.

It is highly recommended to disable runtime compilation for production WAR files.
