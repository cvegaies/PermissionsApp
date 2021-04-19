# Permissions on Android

Enlaces relacionados

[https://developer.android.com/guide/topics/permissions/overview](https://developer.android.com/guide/topics/permissions/overview)

[https://github.com/android/permissions-samples](https://github.com/android/permissions-samples)

A partir de Android 6 (API 23, Android M) la gestión de permisos se modificó, incorporando la solicitud de permisos en tiempo de ejecución.
El esquema de funcionamiento de cómo se deben solicitar los permisos en tiempo de ejecución se resume en la siguiente imagen.

![Imagen que explica el funcionamiento de la petición de permisos en tiempo de ejecución](https://developer.android.com/images/training/permissions/workflow-runtime.svg)

Si se va a a ejecutar alguna **acción cuyo permiso se considera crítico se debe solicitar el permiso de ejecución en tiempo de ejecución**.
Si se desarrolla una aplicación para Android 6 o superior no será necesario comprobar si es necesario solicitar el permiso, ya que lo será siempre.
Si se desarrollo una aplicación para versiones anteriores a Android 6 hay que comprobar si es necesario solicitar permisos.

> if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {  
 ... //comprobar el permiso  
}

También es posible realizar la comprobación directamente con una anotación que indica que ese código sólo es necesario si el Android en el que se ejecuta
es una versión más moderna.

> @SuppressLint("NewApi")  
... //comprobar el permiso

A continuación se debe comprobar si el permiso ya se ha concedido. En caso afirmativo se ejecutará la acción que requiere del permiso.

> if (checkSelfPermission(Manifest.permission.*PERMISO_REQUERIDO*) == PackageManager.PERMISSION_GRANTED) {  
 //ejecutar acción que requiere del permiso  
}

Si no se dispone del permiso se ha de comprobar si se ha de dar una explicación más detallada de la razón por la que se solicita el permiso.
Lo habitual es que la primera vez que se pida el permiso no se dé una explicación detallada y que las siguientes veces sí.

> if(shouldShowRequestPermissionRationale(Manifest.permission.*PERMISO_REQUERIDO*)) {  
 //mostrar explicación detallada por la que se solicita el permiso  
}

La forma en la que se muestra la explicación detallada es libre.

