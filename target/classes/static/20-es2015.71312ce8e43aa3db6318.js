(window.webpackJsonp=window.webpackJsonp||[]).push([[20],{"+tJ3":function(t,o,s){"use strict";s.r(o),s.d(o,"PostDetailModule",(function(){return z}));var e=s("ofXK"),n=s("SxV6"),c=s("fXoL"),i=s("tyNb"),b=s("ova0"),p=s("9ans"),r=s("oXO9"),a=s("q1VI"),d=s("FSzd"),u=s("Phe+");function l(t,o){1&t&&(c.Mb(0,"div"),c.Mb(1,"button",5),c.oc(2," Report "),c.Lb(),c.Lb())}function f(t,o){if(1&t&&(c.Mb(0,"div"),c.Mb(1,"p"),c.oc(2),c.Lb(),c.Mb(3,"p"),c.oc(4),c.Lb(),c.Kb(5,"div",3),c.Kb(6,"app-opinion",4),c.nc(7,l,3,0,"div",1),c.Lb()),2&t){const t=c.Wb();c.zb(2),c.pc(t.post.postID),c.zb(2),c.pc(t.post.title),c.zb(1),c.cc("innerHTML",t.post.text,c.kc),c.zb(1),c.cc("postID",t.post.postID),c.zb(1),c.cc("ngIf",t.isUserAuthenticated)}}function v(t,o){if(1&t&&(c.Mb(0,"div"),c.Kb(1,"app-comment-list",4),c.Lb()),2&t){const t=c.Wb();c.zb(1),c.cc("postID",t.post.postID)}}function h(t,o){if(1&t&&(c.Mb(0,"div",6),c.Kb(1,"app-report-form",4),c.Lb()),2&t){const t=c.Wb();c.zb(1),c.cc("postID",t.post.postID)}}let I=(()=>{class t{constructor(t,o,s,e){this.router=t,this.postService=o,this.auth=s,this.serverService=e}ngOnInit(){this.postService.getPostByPostID(Number.parseInt(this.router.url.split("/")[4],10)).pipe(Object(n.a)()).subscribe(t=>{this.post=t})}get isUserAuthenticated(){return!!this.auth.isAuthenticated()&&this.serverService.isUserAuthenticated}}return t.\u0275fac=function(o){return new(o||t)(c.Jb(i.b),c.Jb(b.a),c.Jb(p.a),c.Jb(r.a))},t.\u0275cmp=c.Db({type:t,selectors:[["app-post-detail"]],decls:4,vars:3,consts:[[1,"container"],[4,"ngIf"],["class","modal","id","myPostModal",4,"ngIf"],[3,"innerHTML"],[3,"postID"],["type","button","data-toggle","modal","data-target","#myPostModal",1,"btn","btn-danger"],["id","myPostModal",1,"modal"]],template:function(t,o){1&t&&(c.Mb(0,"div",0),c.nc(1,f,8,5,"div",1),c.Lb(),c.nc(2,v,2,1,"div",1),c.nc(3,h,2,1,"div",2)),2&t&&(c.zb(1),c.cc("ngIf",o.post),c.zb(1),c.cc("ngIf",o.post),c.zb(1),c.cc("ngIf",o.post))},directives:[e.l,a.a,d.a,u.a],styles:[""]}),t})();var m=s("B8/2"),M=s("K7xu"),g=s("b9zW");let z=(()=>{class t{}return t.\u0275mod=c.Hb({type:t}),t.\u0275inj=c.Gb({factory:function(o){return new(o||t)},imports:[[i.e.forChild([{path:"",component:I}]),e.c,m.a,M.a,g.a]]}),t})()}}]);