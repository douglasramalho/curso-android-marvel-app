##---------------Begin: proguard configuration for Gson  ----------
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# For using GSON @Expose annotation

# nao ofuscar as anotacoes
# o que vai acontecer
# quando chegar aqui na minha classe dataWrapperResponse
# ele vai ofuscar o nome dessa classe
# vai ofuscar tambem o nome copyright
# por exemplo colocando o nome a para copyright mas nao importa pq
# como o gson ta pedido pra nao ofuscar as anotacoes, entao ele automaticamente
# vai conseguir reconhecer que nesse campo, mesmo que ele tenhao o nome a
# nos vamos mapear pro campo a da nossa api
-keepattributes *Annotation*

# Gson specific classes
-dontwarn sun.misc.**
#-keep class com.google.gson.stream.** { *; }

# Application classes that will be serialized/deserialized over Gson
-keep class com.google.gson.examples.android.model.** { <fields>; }


# Prevent proguard from stripping interface information from TypeAdapter, TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
-keep class * extends com.google.gson.TypeAdapter
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

# Prevent R8 from leaving Data object members always null
-keepclassmembers,allowobfuscation class * {
  @com.google.gson.annotations.SerializedName <fields>;
}

# Retain generic signatures of TypeToken and its subclasses with R8 version 3.0 and higher.
-keep,allowobfuscation,allowshrinking class com.google.gson.reflect.TypeToken
-keep,allowobfuscation,allowshrinking class * extends com.google.gson.reflect.TypeToken

# a maioria das lib externas que trabalham com reflexao, vao te fornecer um arquivo ou regras do
# proguard pra vc colocar na sua aplicacao, pq essas regras de cada classe sabem o que pode manter
# ou nao
# o gson como implementa uma lib que trabalha com reflexao
# como eles conhecem quais classes precisam ser mantidas para que a nossa aplicacao
# funcione, com essa biblioteca, como eles tem conhecimento disso, eles fornecem aqui uma regra
#  a classe DataWrapperResponse ainda esta sendo ofuscada
##---------------End: proguard configuration for Gson  ----------