val runAnalyzer = tasks.register<JavaExec>("run") {
    group = "application"
    description = "Runs the repository analyzer. Pass the directory with --args."
    mainClass.set("repoanalyzer.MainKt")
}

gradle.projectsEvaluated {
    val cliMainSourceSet = project(":cli")
        .extensions
        .getByType<SourceSetContainer>()
        .getByName("main")

    runAnalyzer.configure {
        dependsOn(":cli:classes")
        classpath = cliMainSourceSet.runtimeClasspath
    }
}
