.start 
prep init 
call 0 
prep main 
call 0 
halt 
.init
resn 0
push 0
ret
.main
resn 0
push 2
push 1
sub
jumpf l2
drop 1
push 0
jump l3
.l2
push 1
.l3
jumpf l0
push 6
dbg
jump l1
.l0
push 2
push 1
sub
jumpf l6
drop 1
push 0
jump l7
.l6
push 1
.l7
jumpf l4
push 5
dbg
jump l5
.l4
push 4
dbg
.l5
.l1
push 0
ret
