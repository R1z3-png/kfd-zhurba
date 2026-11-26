val runAnalyzer = tasks.register<JavaExec>("run") {
    group = "application"
    description = "Runs the repository analyzer. Pass the directory with --args."
    mainClass.set("repoanalyzer.MainKt")
}

gradle.projectsEvaluated {
    val appMainSourceSet = project(":app")
        .extensions
        .getByType<SourceSetContainer>()
        .getByName("main")

    runAnalyzer.configure {
        dependsOn(":app:classes")
        classpath = appMainSourceSet.runtimeClasspath
    }
}
