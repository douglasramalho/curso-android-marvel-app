# M1 - Estrutura base

### Estrutura base e definições da arquitetura da aplicação

- Decisões importantes a serem tomadas no início do projeto
- Introdução a Clean Archtecture
- Arquitetura e modulos do projeto
- Análise estática de código (Lint, Ktlint, Detekt)
- Configuração do Detekt no projeto

[https://detekt.dev/#quick-start-with-gradle](https://detekt.dev/#quick-start-with-gradle)

1. Habilitar as tasks na aba do gradle - Settings > Experimental > task list
2. Executar a task - verification > detektGerenateConfig
3. É criado um arquivo ‘detekt.yml’ na pasta config do projeto. Nele contém as regras do Detekt
4. maxIssues = quantidades de problemas aceitaveis
5. Criar um arquivo detekt.gradle na pasta detekt e colar o script abaixo

```jsx
detekt {
    toolVersion = "1.19.0"
    config = files("config/detekt/detekt.yml")
    buildUponDefaultConfig = true
}
```

1. Adicionar o rootDir no arquivo para poder ser importado no modulo app
2. Adicionar variavel de versão do detekt no build.gradle do projeto, e depois referenciar nos imports

```jsx
ext.detekt_version = "1.19.0"
```

1. Remover `buildUponDefaultConfig = *true*`
2. Adicionar `autoCorrect = *true*`
3. Como temos mais de um módulo precisamos adicionar esta configuração

```jsx
def inputDirFiles = []
rootProject.subprojects.each {module ->
    inputDirFiles << "$module.projectDir/src/main/java"
}
source = files(inputDirFiles)
```

1. Configuração de relatório

```jsx
reports {
        xml {
            enabled = true
            destination = file("$buildDir/reports/detekt/detekt.xml")
        }
        html {
            enabled = true
            destination = file("$buildDir/reports/detekt/detekt.html")
        }
        txt {
            enabled = true
            destination = file("$buildDir/reports/detekt/detekt.txt")
        }
    }
```

1. Importar o detekt.gradle no build.gradle do App

```jsx
apply from: '../config/detekt/detekt.gradle'
```

1. Configuração do Detekt realizada,  a partir de agora pode ser executado a regra pelo detekt.gradle ou pelo aba gradle do detekt
2. Para executar a task automaticamento no build do projeto, vamos precisamos adicionar a configuração no arquivo detekt.gradle

```jsx
apply plugin: "io.gitlab.arturbosch.detekt"

project.afterEvaluate {
    tasks.named("preBuild") {
        dependsOn("detekt")
    }
}
```