Ext.define("Ext.form.field.VTypes",(function(){var C=/^[a-zA-Z_]+$/,D=/^[a-zA-Z0-9_]+$/,A=/^(")?(?:[^\."])(?:(?:[\.])?(?:[\w\-!#$%&'*+/=?^_`{|}~]))*\1@(\w[\-\w]*\.){1,5}([A-Za-z]){2,6}$/,B=/(((^https?)|(^ftp)):\/\/((([\-\w]+\.)+\w{2,3}(\/[%\-\w]+(\.\w{2,})?)*(([\w\-\.\?\\\/+@&#;`~=%!]*)(\.\w{2,})?)*)|(localhost|LOCALHOST))\/?)/i;return{singleton:true,alternateClassName:"Ext.form.VTypes","email":function(E){return A.test(E)},"emailText":'This field should be an e-mail address in the format "user@example.com"',"emailMask":/[\w.\-@'"!#$%&'*+/=?^_`{|}~]/i,"url":function(E){return B.test(E)},"urlText":'This field should be a URL in the format "http://www.example.com"',"alpha":function(E){return C.test(E)},"alphaText":"This field should only contain letters and _","alphaMask":/[a-z_]/i,"alphanum":function(E){return D.test(E)},"alphanumText":"This field should only contain letters, numbers and _","alphanumMask":/[a-z0-9_]/i}}()));