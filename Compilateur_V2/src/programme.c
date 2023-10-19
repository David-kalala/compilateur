int main () {


 	int t;
 	t = malloc(5); 
    t[0] = 2;  
    t[1] = 4;
    t[2] = 5;
    t[3] = 6;
    t[4] = 8;
   
   	int b;
   	
	for(b = 0; b<5; b = b+1){debug t[b];}
}