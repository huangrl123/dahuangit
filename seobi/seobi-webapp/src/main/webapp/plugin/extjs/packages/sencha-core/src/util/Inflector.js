Ext.define("Ext.util.Inflector",{singleton:true,plurals:[[(/(quiz)$/i),"$1zes"],[(/^(ox)$/i),"$1en"],[(/([m|l])ouse$/i),"$1ice"],[(/(matr|vert|ind)ix|ex$/i),"$1ices"],[(/(x|ch|ss|sh)$/i),"$1es"],[(/([^aeiouy]|qu)y$/i),"$1ies"],[(/(hive)$/i),"$1s"],[(/(?:([^f])fe|([lr])f)$/i),"$1$2ves"],[(/sis$/i),"ses"],[(/([ti])um$/i),"$1a"],[(/(buffal|tomat|potat)o$/i),"$1oes"],[(/(bu)s$/i),"$1ses"],[(/(alias|status|sex)$/i),"$1es"],[(/(octop|vir)us$/i),"$1i"],[(/(ax|test)is$/i),"$1es"],[(/^(p)erson$/i),"$1eople"],[(/^(m)an$/i),"$1en"],[(/(.*)(child)(ren)?$/i),"$1$2ren"],[(/s$/i),"s"],[(/$/),"s"]],singulars:[[(/(address)$/i),"$1"],[(/(quiz)zes$/i),"$1"],[(/(matr)ices$/i),"$1ix"],[(/(vert|ind)ices$/i),"$1ex"],[(/^(ox)en/i),"$1"],[(/(alias|status)es$/i),"$1"],[(/(octop|vir)i$/i),"$1us"],[(/(cris|ax|test)es$/i),"$1is"],[(/(shoe)s$/i),"$1"],[(/(o)es$/i),"$1"],[(/(bus)es$/i),"$1"],[(/([m|l])ice$/i),"$1ouse"],[(/(x|ch|ss|sh)es$/i),"$1"],[(/(m)ovies$/i),"$1ovie"],[(/(s)eries$/i),"$1eries"],[(/([^aeiouy]|qu)ies$/i),"$1y"],[(/([lr])ves$/i),"$1f"],[(/(tive)s$/i),"$1"],[(/(hive)s$/i),"$1"],[(/([^f])ves$/i),"$1fe"],[(/(^analy)ses$/i),"$1sis"],[(/((a)naly|(b)a|(d)iagno|(p)arenthe|(p)rogno|(s)ynop|(t)he)ses$/i),"$1$2sis"],[(/([ti])a$/i),"$1um"],[(/(n)ews$/i),"$1ews"],[(/(p)eople$/i),"$1erson"],[(/s$/i),""]],uncountable:["sheep","fish","series","species","money","rice","information","equipment","grass","mud","offspring","deer","means"],singular:function(A,B){this.singulars.unshift([A,B])},plural:function(A,B){this.plurals.unshift([A,B])},clearSingulars:function(){this.singulars=[]},clearPlurals:function(){this.plurals=[]},isTransnumeral:function(A){return Ext.Array.indexOf(this.uncountable,A)!=-1},pluralize:function(F){if(this.isTransnumeral(F)){return F}var D=this.plurals,A=D.length,E,C,B;for(B=0;B<A;B++){E=D[B];C=E[0];if(C==F||(C.test&&C.test(F))){return F.replace(C,E[1])}}return F},singularize:function(F){if(this.isTransnumeral(F)){return F}var D=this.singulars,A=D.length,E,C,B;for(B=0;B<A;B++){E=D[B];C=E[0];if(C==F||(C.test&&C.test(F))){return F.replace(C,E[1])}}return F},classify:function(A){return Ext.String.capitalize(this.singularize(A))},ordinalize:function(D){var C=parseInt(D,10),B=C%10,A=C%100;if(11<=A&&A<=13){return D+"th"}else{switch(B){case 1:return D+"st";case 2:return D+"nd";case 3:return D+"rd";default:return D+"th"}}}},function(){var A={alumnus:"alumni",cactus:"cacti",focus:"foci",nucleus:"nuclei",radius:"radii",stimulus:"stimuli",ellipsis:"ellipses",paralysis:"paralyses",oasis:"oases",appendix:"appendices",index:"indexes",beau:"beaux",bureau:"bureaux",tableau:"tableaux",woman:"women",child:"children",man:"men",corpus:"corpora",criterion:"criteria",curriculum:"curricula",genus:"genera",memorandum:"memoranda",phenomenon:"phenomena",foot:"feet",goose:"geese",tooth:"teeth",antenna:"antennae",formula:"formulae",nebula:"nebulae",vertebra:"vertebrae",vita:"vitae"},B;for(B in A){if(A.hasOwnProperty(B)){this.plural(B,A[B]);this.singular(A[B],B)}}});