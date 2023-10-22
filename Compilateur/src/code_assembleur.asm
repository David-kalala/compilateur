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
resn 4
prep malloc
push 2
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
get 1
add 
read 
dbg 
get 1
push 5
cmplt
jumpf l5
push 0
dup
set 3
drop 1 
.l6
get 3
push 5
cmplt
jumpf l9
push 1234
dbg 
.l8
get 3
push 1
add 
dup
set 3
drop 1 
jump l10
.l9
jump l7
.l10
jump l6
.l7
push 88
dup
set 2
drop 1 
get 2
dbg 
.l5
.l2
get 2
push 1
add 
dup
set 2
drop 1 
jump l4
.l3
jump l7
.l4
jump l0
.l7
push 0
ret
.start 
prep init 
call 0 
prep main 
call 0 
halt 
