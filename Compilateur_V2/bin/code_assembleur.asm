.init
resn 0
push 0
ret
.main
resn 2
push 3
dup
set 1
drop 1 
get 1
dup
get 0
push 0
add 
write 
drop 1 
get 0
read 
dbg 
push 4
dup
get 0
push 8
add 
write 
drop 1 
get 0
read 
dbg 
push 0
ret
.start 
prep init 
call 0 
prep main 
call 0 
halt 
