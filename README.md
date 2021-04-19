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

También es posible realizar la comprobación directamente con una anotación que indica que ese código sólo es necesario si la versión de Android en el que se ejecuta
es una versión más moderna.

> @SuppressLint("NewApi")  
... //comprobar el permiso

A continuación se debe comprobar si el permiso ya se ha concedido (4). En caso afirmativo se ejecutará la acción que requiere del permiso.

> if (checkSelfPermission(Manifest.permission.*PERMISO_REQUERIDO*) == PackageManager.PERMISSION_GRANTED) {  
 //ejecutar acción que requiere del permiso (8a)  
}

Si no se dispone del permiso se ha de comprobar si se ha de dar una explicación más detallada de la razón por la que se solicita el permiso.
Lo habitual es que la primera vez que se pida el permiso no se dé una explicación detallada y que las siguientes veces sí, siempre y cuando
no se haya dado el permiso (5a).

> if(shouldShowRequestPermissionRationale(Manifest.permission.*PERMISO_REQUERIDO*)) {  
 //mostrar explicación detallada por la que se solicita el permiso (5b)  
}

La forma en la que se muestra la explicación detallada es libre. Una vez mostrada esta explicación, se solicita el permiso.

Para solicitar un permiso (6) se debe indicar el o los permisos y una constante numérica entera para identificar la solicitud de permiso.

> requestPermissions(new String[]{Manifest.permission.*PERMISO_REQUERIDO*}, CTE_PERMISO_SOLICITADO);

Se abre un proceso propio de Android en el que se solicita el permiso requerido.

<img src="https://developer.android.com/images/training/permissions/one-time-prompt.svg" alt="Imagen que muestra un ejemplo de cómo se solicita un permiso" width="300"/>

El usuario podrá responder denegando o permitiendo el acceso al
recurso protegido. Una vez concluido este proceso, se vuelve a la aplicación. Se ejecutará el método onRequestPermissionsResult(), que es un
método del *lifecycle* de la actividad.

> @Override  
public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {  
 super.onRequestPermissionsResult(requestCode, permissions, grantResults);  
 switch(requestCode) {  
  case CTE_PERMISO_SOLICITADO:  
   if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {  
    //ejecutar acción que requiere del permiso (8a)  
   } else {  
    //ejecutar acción en caso de no haber concedido el permiso (8b)  
   }  
  break;  
 }  
}

