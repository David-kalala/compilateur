.init
resn 0
push 0
ret
.main
resn 2
get 0
read 
drop 1 
push 3
dup
get 0
write 
drop 1 
push 4
dup
get 0
write 
drop 1 
push 0
dup
set 1
drop 1 
.l0
get 1
push 2
cmplt
jumpf l3
get 0
read 
dbg 
.l2
get 1
push 1
add 
dup
set 1
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
