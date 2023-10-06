.init
resn 0
push 0
ret
.addition
resn 1
get 0
get 1
add 
dup
set 2
drop 1 
get 2
ret 
push 0
ret
.multiplication
resn 1
get 0
get 1
mul 
dup
set 2
drop 1 
get 2
ret 
push 0
ret
.division
resn 1
get 0
get 1
div
dup
set 2
drop 1 
get 2
ret 
push 0
ret
.soustraction
resn 1
get 0
get 1
sub 
dup
set 2
drop 1 
get 2
ret 
push 0
ret
.main
resn 0
prep addition
push 1
push 1
call 2
dbg 
prep multiplication
push 2
push 3
call 2
dbg 
prep division
push 10
push 5
call 2
dbg 
prep soustraction
push 20
push 5
call 2
dbg 
push 0
ret
.start 
prep init 
call 0 
prep main 
call 0 
halt 
