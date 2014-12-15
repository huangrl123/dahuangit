Ext.define("Ext.data.SortTypes",{singleton:true,none:Ext.identityFn,stripTagsRE:/<\/?[^>]+>/gi,asText:function(A){return String(A).replace(this.stripTagsRE,"")},asUCText:function(A){return String(A).toUpperCase().replace(this.stripTagsRE,"")},asUCString:function(A){return String(A).toUpperCase()},asDate:function(A){if(!A){return 0}if(Ext.isDate(A)){return A.getTime()}return Date.parse(String(A))},asFloat:function(A){var B=parseFloat(String(A).replace(/,/g,""));return isNaN(B)?0:B},asInt:function(A){var B=parseInt(String(A).replace(/,/g,""),10);return isNaN(B)?0:B}});