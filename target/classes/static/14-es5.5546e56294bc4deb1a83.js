function _classCallCheck(e,t){if(!(e instanceof t))throw new TypeError("Cannot call a class as a function")}function _defineProperties(e,t){for(var r=0;r<t.length;r++){var n=t[r];n.enumerable=n.enumerable||!1,n.configurable=!0,"value"in n&&(n.writable=!0),Object.defineProperty(e,n.key,n)}}function _createClass(e,t,r){return t&&_defineProperties(e.prototype,t),r&&_defineProperties(e,r),e}(window.webpackJsonp=window.webpackJsonp||[]).push([[14],{GU6H:function(e,t,r){"use strict";r.r(t),r.d(t,"ServerDetailModule",(function(){return y}));var n,s=r("ofXK"),i=r("fXoL"),o=r("oXO9"),c=r("tyNb"),a=r("9ans"),u=r("3Pt+"),l=r("SxV6"),h=((n=function(){function e(t,r){_classCallCheck(this,e),this.serverService=t,this.router=r}return _createClass(e,[{key:"ngOnInit",value:function(){this.userForm=new u.e({name:new u.c(null,[u.o.required])})}},{key:"submit",value:function(){var e=Number.parseInt(this.router.url.split("/")[2],10);this.serverService.createUser({userName:this.userForm.value.name,serverID:e}).pipe(Object(l.a)()).subscribe((function(e){})),this.router.navigate(["/server/"+e+"/posts"])}}]),e}()).\u0275fac=function(e){return new(e||n)(i.Jb(o.a),i.Jb(c.b))},n.\u0275cmp=i.Db({type:n,selectors:[["app-user-registration"]],decls:7,vars:2,consts:[[3,"formGroup","ngSubmit"],[1,"field"],["for","name"],["id","name","placeholder","User  server name","formControlName","name"],["type","submit",3,"disabled"]],template:function(e,t){1&e&&(i.Mb(0,"div"),i.Mb(1,"form",0),i.Ub("ngSubmit",(function(){return t.submit()})),i.Mb(2,"div",1),i.Kb(3,"label",2),i.Kb(4,"input",3),i.Lb(),i.Mb(5,"button",4),i.oc(6," send "),i.Lb(),i.Lb(),i.Lb()),2&e&&(i.zb(1),i.cc("formGroup",t.userForm),i.zb(4),i.cc("disabled",t.userForm.invalid))},directives:[u.q,u.k,u.f,u.b,u.j,u.d],styles:[""]}),n);function f(e,t){1&e&&(i.Mb(0,"div"),i.Kb(1,"app-user-registration"),i.Lb())}function p(e,t){if(1&e&&(i.Mb(0,"div"),i.nc(1,f,2,0,"div",1),i.Lb()),2&e){var r=i.Wb();i.zb(1),i.cc("ngIf",!r.isUserAuthenticated)}}var v,b,d,m=((d=function(){function e(t,r,n){_classCallCheck(this,e),this.serverService=t,this.router=r,this.auth=n}return _createClass(e,[{key:"ngOnInit",value:function(){this.serverService.getUserProfile(this.router.url.split("/")[2])}},{key:"isAuthenticated",get:function(){return this.auth.isAuthenticated()}},{key:"isUserAuthenticated",get:function(){return this.serverService.isUserAuthenticated}}]),e}()).\u0275fac=function(e){return new(e||d)(i.Jb(o.a),i.Jb(c.b),i.Jb(a.a))},d.\u0275cmp=i.Db({type:d,selectors:[["app-server-detail"]],decls:2,vars:1,consts:[[1,"container"],[4,"ngIf"]],template:function(e,t){1&e&&(i.Mb(0,"div",0),i.nc(1,p,2,1,"div",1),i.Lb()),2&e&&(i.zb(1),i.cc("ngIf",t.isAuthenticated))},directives:[s.l,h],styles:[""]}),d),k=((b=function e(){_classCallCheck(this,e)}).\u0275mod=i.Hb({type:b}),b.\u0275inj=i.Gb({factory:function(e){return new(e||b)},imports:[[s.c,u.g,u.n]]}),b),y=((v=function e(){_classCallCheck(this,e)}).\u0275mod=i.Hb({type:v}),v.\u0275inj=i.Gb({factory:function(e){return new(e||v)},imports:[[c.e.forChild([{path:"",component:m}]),s.c,k]]}),v)},oXO9:function(e,t,r){"use strict";r.d(t,"a",(function(){return c}));var n=r("tk/3"),s=r("SxV6"),i=r("fXoL"),o=r("9ans"),c=function(){var e=function(){function e(t,r){_classCallCheck(this,e),this.http=t,this.authService=r,this.user=void 0}return _createClass(e,[{key:"getServerList",value:function(e){return this.http.get("http://localhost:8085/server/list/"+e)}},{key:"getServerPageCountList",value:function(e){return this.http.get("http://localhost:8085/server/pages/"+e)}},{key:"createServer",value:function(e){return this.http.post("http://localhost:8085/server/create",e,{headers:new n.c({token:this.authService.token})})}},{key:"getUserProfile",value:function(e){var t=this;this.authService.isAuthenticated()&&this.http.get("http://localhost:8085/server/get_user/"+e,{headers:new n.c({token:this.authService.token})}).pipe(Object(s.a)()).subscribe((function(e){return t.user=e}))}},{key:"createUser",value:function(e){return this.http.post("http://localhost:8085/server/create_user",e,{headers:new n.c({token:this.authService.token})})}},{key:"sendReportsByPost",value:function(e,t){return console.log("send like post report"),this.http.post("http://localhost:8085/server/report?postID="+t+"&commentID=0",e,{headers:new n.c({token:this.authService.token})})}},{key:"sendReportsByComment",value:function(e,t){return console.log("send like comment report"),this.http.post("http://localhost:8085/server/report?commentID="+t+"&postID=0",e,{headers:new n.c({token:this.authService.token})})}},{key:"isUserAuthenticated",get:function(){return null!==this.user}}]),e}();return e.\u0275fac=function(t){return new(t||e)(i.Qb(n.a),i.Qb(o.a))},e.\u0275prov=i.Fb({token:e,factory:e.\u0275fac,providedIn:"root"}),e}()}}]);