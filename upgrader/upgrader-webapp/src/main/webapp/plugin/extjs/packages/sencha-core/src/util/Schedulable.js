Ext.define("Ext.util.Schedulable",{"abstract":true,isSchedulable:true,scheduled:false,constructor:function(){this.getScheduler().add(this)},destroy:function(){var B=this,A=B.getScheduler();if(A){A.remove(B)}B.destroyed=true;B.scheduler=null;B.schedule=B.destroy=B.react=Ext.emptyFn},getFullName:function(){return this.name||this.id},privates:{getScheduler:function(){return this.scheduler},schedule:function(){var B=this,A;if(!B.scheduled){A=B.getScheduler();if(A){B.scheduled=true;if(B.onSchedule){B.onSchedule()}A.scheduleItem(B)}}},unschedule:function(){var B=this,A;if(B.scheduled){A=B.getScheduler();if(A){A.unscheduleItem(B)}B.scheduled=false}},sort:function(){}}});