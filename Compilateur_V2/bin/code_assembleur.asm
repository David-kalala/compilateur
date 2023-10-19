.init
resn 0
push 0
ret
.printNum
resn 0
push 0
ret
.malloc
resn 1
push 0
read 
dup
set 1
drop 1 
push 0
read 
get 0
add 
dup
push 0
write 
drop 1 
get 0
ret 
push 0
ret
.free
resn 0
push 0
ret
.main
resn 2
prep malloc
push 5
call 1
dup
set 0
drop 1 
push 2
dup
get 0
push 0
add 
write 
drop 1 
push 4
dup
get 0
push 1
add 
write 
drop 1 
push 5
dup
get 0
push 2
add 
write 
drop 1 
push 6
dup
get 0
push 3
add 
write 
drop 1 
push 8
dup
get 0
push 4
add 
write 
drop 1 
push 0
dup
set 1
drop 1 
.l0
get 1
push 5
cmplt
jumpf l3
get 0
get 1
add 
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
