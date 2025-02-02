public class Main {
//    public static void main(String[] args) {
//
//        System.out.println("Hello world!");
//        Board board=new Board();
//        board.printBoard();
//        board.makeMove(0,2);
//        board.printBoard();
//        board.makeMove(1,2);
//        board.printBoard();
//        System.out.println(Integer.MAX_VALUE+"  "+Integer.MIN_VALUE);
//    }

    public static void main(String[] args) {
        Board board = new Board();
        int d = 12;
        PCPlayer playerOne=new PCPlayer(0,1,2, d); //id, Heuristic type ,mode(human or pc), depth
        PCPlayer playerTwo=new PCPlayer(1,3,2, d); //1 2 10 1 2 12

        int turn=0;
        int prevTurn = -1;
        int move=-1;

        board.printBoard();

        while(!board.IsGameOver()){
            if(turn == Integer.MIN_VALUE){
                System.out.println("Incorrect move that should not happen. Try again");
                turn = prevTurn;
            }
            prevTurn = turn;
            System.out.println("Player "+turn+"'s turn");

            if(turn==0){
                move = playerOne.makeMove(new Board(board));
            }else if(turn==1){
                move = playerTwo.makeMove(new Board(board));
            }else{
                System.out.println("This should not happen . invalid turn id");
            }
            //System.out.println("Turn: "+turn+" Move: "+move);
            System.out.println("Move of Player "+turn+": "+move);
            turn = board.makeMove(turn,move);
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println("---------------------------------------------------------");
            //turn = board.makeMove(0,2);
            board.printBoard();

            if(!board.isValid()){
                System.out.println("BOARD IS NOT VALID");
            }
        }
        int winner = board.getWinner();
        if(winner>=0){
            System.out.println("--------------------GAME OVER----------------------------");
            board.printBoard();
            System.out.println("WINNER: Player "+winner);
        }else{
            System.out.println("--------------------GAME OVER-----------------------------");
            board.printBoard();
            System.out.println("The game is draw");
        }
    }
}