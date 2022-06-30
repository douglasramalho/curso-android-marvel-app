-dontobfuscate

# nao ofusca
# manter os atributos do SourceFile e LineNumberTable
# Fazendo isso aqui, vamos permitir o debug no build de staging
# Mantendo a configuracao de release que eh a parte de ofuscacao de codigo, reducao etc
# pra vc debugar vc precisar criar um novo arquivo
-keepattributes SourceFile, LineNumberTable
