(window.webpackJsonp=window.webpackJsonp||[]).push([[19],{HBr1:function(t,e,o){"use strict";o.r(e),o.d(e,"PostCreateModule",(function(){return a}));var r=o("ofXK"),n=o("3Pt+"),s=o("SxV6"),i=o("fXoL"),u=o("tyNb"),c=o("ova0"),b=o("CzEO");let p=(()=>{class t{constructor(t,e,o){this.router=t,this.route=e,this.postService=o}ngOnInit(){this.postForm=new n.e({title:new n.c(null),text:new n.c(null)})}sendPost(){const t=Number.parseInt(this.router.url.split("/")[2],10);this.postService.create({title:this.postForm.value.title,text:this.postForm.value.text,serverID:t}).pipe(Object(s.a)()).subscribe(e=>{this.router.navigate(["/server/"+t+"/posts"])})}}return t.\u0275fac=function(e){return new(e||t)(i.Jb(u.b),i.Jb(u.a),i.Jb(c.a))},t.\u0275cmp=i.Db({type:t,selectors:[["app-post-create"]],decls:9,vars:1,consts:[[1,"container"],[3,"formGroup","ngSubmit"],["formControlName","title","type","text",1,"input-group-prepend","input-group-text"],["formControlName","text"],[1,"btn","btn-success"]],template:function(t,e){1&t&&(i.Mb(0,"div",0),i.Mb(1,"form",1),i.Ub("ngSubmit",(function(){return e.sendPost()})),i.Mb(2,"label"),i.oc(3," Title: "),i.Kb(4,"input",2),i.Lb(),i.Mb(5,"div"),i.Kb(6,"quill-editor",3),i.Lb(),i.Mb(7,"button",4),i.oc(8,"Send"),i.Lb(),i.Lb(),i.Lb()),2&t&&(i.zb(1),i.cc("formGroup",e.postForm))},directives:[n.q,n.k,n.f,n.b,n.j,n.d,b.a],styles:[""]}),t})(),a=(()=>{class t{}return t.\u0275mod=i.Hb({type:t}),t.\u0275inj=i.Gb({factory:function(e){return new(e||t)},imports:[[u.e.forChild([{path:"",component:p}]),r.c,n.g,n.n,b.b]]}),t})()}}]);