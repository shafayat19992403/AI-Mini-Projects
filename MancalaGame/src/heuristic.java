public class heuristic {

    public static int W_STORAGE = 5;
    public static int W_PITS = 1;
    public static int W_MOVES = 10;
    public static int W_CLOSE_TO_WIN = 10;
    public static int W_CAPTURED = 1;
    public static int W_EXTRA = 5;
    public static int heuristicOne(int id,Board board){
        return board.storage[id] - board.storage[1-id];
    }
    public static int heuristicTwo(int id,Board board){
        int stonesInCurrentPlayerPit = 0;
        int stonesInOpponentPlayerPit = 0;

        for(int i=0 ; i< Board.NO_PITS; i++){
            stonesInCurrentPlayerPit += board.pit[id][i];
            stonesInOpponentPlayerPit += board.pit[1-id][i];
        }

        return W_STORAGE * (board.storage[id] - board.storage[1-id]) + W_PITS * (stonesInCurrentPlayerPit - stonesInOpponentPlayerPit);
    }

    public static int heuristicThree(int id,int extra, Board board){
        int result = heuristicTwo(id, board) + W_MOVES * extra;
        return result;
    }

    public static int heuristicFour(int id,int extra, Board board,Board prevBoard){
        int captured = 0;

        for(int i = 0; i<Board.NO_PITS;i++){
            if(prevBoard.pit[id][i]==0){
                if(board.pit[id][i]==0){
                    if(prevBoard.pit[1-id][Board.NO_PITS-i-1] != 0){
                        if(board.pit[1-id][Board.NO_PITS-i-1]==0){
                            captured += prevBoard.pit[1-id][Board.NO_PITS-i-1];
                        }
                    }
                }
            }
        }
        return W_CAPTURED * captured + heuristicThree(id,extra,board) ;
    }
}
