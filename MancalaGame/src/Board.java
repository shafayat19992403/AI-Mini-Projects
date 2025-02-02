public class Board {
    public static int NO_PITS=6;
    public static int NO_BEADS=4;

    public int[][] pit;
    public int[] storage;

    public Board(){
        pit = new int[2][NO_PITS];
        storage = new int[2];
        for(int i=0;i<2;i++){
            for(int j=0;j<NO_PITS;j++){
                pit[i][j] = NO_BEADS;
            }
            storage[i] = 0;
        }
    }

    public Board(Board board){
        pit= new int[2][NO_PITS];
        storage = new int[2];
        for(int i=0;i<2;i++){
            for(int j=0;j<NO_PITS;j++){
                pit[i][j] = board.pit[i][j];
            }
            storage[i]=board.storage[i];
        }
    }

    public void makeCopy(Board board) {
        //pit=board.pit;
        for(int i=0;i<2;i++){
            for(int j=0;j<NO_PITS;j++){
                pit[i][j] = board.pit[i][j];
            }
            storage[i] = board.storage[i];
        }
    }

    public boolean isValid(){
        int temp=0;
        for(int i=0;i<2 ; i++){
            for(int j=0;j<NO_PITS;j++){
                temp+=pit[i][j];
            }
            temp+=storage[i];
        }
        return (temp == NO_PITS*NO_BEADS*2);
    }

    public int makeMove(int id, int pitPosition){
        //System.out.println("ID: "+id+"PitPos: "+pitPosition);
        int availableBeads = pit[id][pitPosition];
        pit[id][pitPosition]=0;

        if((pitPosition > NO_PITS) || (pitPosition<0) || (availableBeads==0)){
            return Integer.MIN_VALUE;
        }

        int opponentId = (1 - id);
        int nextPlayer = opponentId;
        int totalPit = NO_PITS * 2 + 1;
        int beadsPerPit = (int) Math.floor(availableBeads/totalPit);
        int beadsLeft = availableBeads % totalPit;
        //System.out.println("BEADS_LEFT: "+beadsLeft);

        for(int i=0;i<NO_PITS;i++){
            pit[id][i]+= beadsPerPit;
            pit[opponentId][i] += beadsPerPit;
        }
        storage[id] += beadsPerPit;

        if(availableBeads == NO_PITS * 2 + 1){
            storage[id] += pit[opponentId][NO_PITS - pitPosition - 1];
            storage[id] += 1;
            //System.out.println("Store upped");
            pit[opponentId][NO_PITS - pitPosition -1]=0;
            pit[id][pitPosition] = 0;
        }

        for(int i = pitPosition + 1 ;i < NO_PITS ; i++){
            if(beadsLeft > 0){
                pit[id][i]+=1;
                beadsLeft--;

                if((beadsLeft==0) && (pit[id][i] == 1)){
                    if(pit[opponentId][NO_PITS - i -1]>0){
                        storage[id] += pit[opponentId][NO_PITS - i -1];
                        storage[id] += 1;
                        //System.out.println("Store upped");
                        pit[opponentId][NO_PITS - i - 1]=0;
                        pit[id][i]=0;
                    }
                }
            }
        }

        if(beadsLeft > 0) {
            storage[id] +=1;
            beadsLeft--;
            if(beadsLeft == 0){
                nextPlayer = id;
            }
        }

        for(int i=0; i< NO_PITS ; i++){
            if(beadsLeft > 0){
                pit[opponentId][i] += 1;
                beadsLeft--;
            }
        }

        for(int i=0; i < pitPosition + 1 ; i++){
            if(beadsLeft > 0){
                pit[id][i] += 1;
                beadsLeft--;

                if((beadsLeft == 0) && (pit[id][i] == 1)){
                    if(pit[opponentId][NO_PITS - i - 1]>0){
                        storage[id] += pit[opponentId][NO_PITS - i -1];
                        storage[id] += 1;
                        //System.out.println("Store upped");
                        pit[opponentId][NO_PITS - i - 1]=0;
                        pit[id][i] = 0;

                    }
                }
            }
        }
        //printBoard();
        return nextPlayer;
    }

   public boolean IsGameOver(){
        if(storage[0] + storage[1] == NO_PITS * NO_BEADS * 2){
            return true;
        }
        int temp = 0;
        for(int i=0 ; i<NO_PITS ; i++){
            temp += pit[0][i];
        }
        if(temp == 0) return true;

        temp = 0;

        for(int i=0 ; i< NO_PITS ; i++){
            temp += pit[1][i];
        }
        if(temp == 0) return true;


        return false;
    }

    int getWinner(){
        if(!IsGameOver()){
            System.out.println("Game is running .... Not over yet!");
            return Integer.MIN_VALUE;
        }

        int playerZeroBeads = storage[0];
        int playerOneBeads = storage[1];

        for(int i=0 ; i<NO_PITS ; i++){
            playerOneBeads += pit[1][i];
            playerZeroBeads += pit[0][i];
            pit[0][i]=0;pit[1][i]=0;
        }

        storage[0] = playerZeroBeads;
        storage[1] = playerOneBeads;

        if(playerOneBeads > playerZeroBeads){
            return 1;
        }else if(playerZeroBeads > playerOneBeads){
            return 0;
        }else{
            return -1;
        }
    }

    void printBoard(){
        System.out.println("Player 1");
        System.out.print("        ");
        for(int i=0 ; i<NO_PITS ; i++){
            System.out.print("< "+(NO_PITS - i - 1) +" >");
        }

        System.out.println("        ");
        System.out.print("        ");
        for(int i=0; i<NO_PITS;i++){
            System.out.print("  " + pit[1][NO_PITS-i-1] + "  ");
        }
        System.out.println();
        System.out.print("  ");

        System.out.print(storage[1]);

        for(int i=0; i<NO_PITS; i++){
            System.out.print("     ");
        }
        System.out.print("          ");
        System.out.print(storage[0]);
        System.out.println();
        System.out.print("        ");
        for(int i=0 ; i<NO_PITS;i++){
            System.out.print("  "+pit[0][i]+"  ");
        }
        System.out.println();
        System.out.print("        ");
        for(int i=0 ; i<NO_PITS ; i++){
            System.out.print("< "+i +" >");
        }
        System.out.println();
        System.out.print("        "+"        "+"        "+"        "+"        ");
        System.out.println("Player 0");

    }
}
