.init
resn 0
push 0
ret
.main
resn 1
push 1
dup
set 0
drop 1 
.l0
get 0
push 3
cmplt
jumpf l3
push 1
dbg 
.l2
get 0
push 1
add 
dup
set 0
drop 1 
jump l4
.l3
jump l1
.l4
jump l0
.l1
push 0
ret
.start 
prep init 
call 0 
prep main 
call 0 
halt 
