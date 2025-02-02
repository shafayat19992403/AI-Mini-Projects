import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;

public class PCPlayer {
    public int id;
    public int hNumber;
    public int extraMoves;
    public int mode;
    public int depth;

    public PCPlayer(int id,int hNumber,int mode,int depth){
        this.id=id;
        this.hNumber=hNumber;
        this.mode=mode;
        this.depth=depth;
    }

    public int makeMove(Board board){
        if(mode == 1){
            return humanUserMove();
        }else if(mode == 2){
            extraMoves=0;
            int move = minMaxAlgo(new Board(board),new Board(board),true,depth,Integer.MIN_VALUE,Integer.MAX_VALUE);
//            System.out.println("Move of Player "+id+": "+move);
            return move;
        }else{
            return -1;
        }
    }

    int calculateHValue(Board board,Board prevBoard) {
        if(hNumber == 1){
            return heuristic.heuristicOne(id,board);
        }else if(hNumber == 2){
            return heuristic.heuristicTwo(id,board);
        }else if(hNumber == 3){
            return heuristic.heuristicThree(id,extraMoves,board);
        }else if(hNumber == 4){
            return heuristic.heuristicFour(id,extraMoves,board,prevBoard);
        }else{
            return -1;
        }
    }

    int humanUserMove(){
        Scanner scanner=new Scanner(System.in);
        int move=scanner.nextInt();
        return move;
    }

    int minMaxAlgo(Board board,Board prevBoard, boolean isMax, int depthToGo,int alpha,int beta){
        if(board.IsGameOver()) {
            if (board.getWinner() == id) {
                return Integer.MAX_VALUE / 2;
            } else if (board.getWinner() == 1 - id) {
                return Integer.MIN_VALUE / 2;
            }else {
                return calculateHValue(board,prevBoard);
            }
        }

        if(depthToGo == 0){
            return calculateHValue(board,prevBoard);
        }

        Board boardCopy = new Board();
        boardCopy.makeCopy(board);

        if(isMax){
            int bestValue = Integer.MIN_VALUE;
            int successor = -1;

            ArrayList<Integer> moveTracker=new ArrayList<>();
//            ArrayList<Integer> moveTrackerRest=new ArrayList<>();
//
//            for(int i=0;i < Board.NO_PITS;i++){
//                if(board.pit[id][i]+i == Board.NO_PITS){
//                    moveTracker.add(i);
//                }else{
//                    moveTrackerRest.add(i);
//                }
//            }
//
//            for(int i=0; i<moveTrackerRest.size();i++){
//                moveTracker.add(moveTrackerRest.get(i));
//            }
            for(int i=0;i<Board.NO_PITS;i++){
                moveTracker.add(i);
            }

            Collections.shuffle(moveTracker);

            for(int i=0;i<Board.NO_PITS;i++){
                int currMove = moveTracker.get(i);

                if(board.pit[id][currMove] > 0){
                    int nextPlayer = board.makeMove(id,currMove);
                    int currVal;

                    if(nextPlayer == id){
                        extraMoves++;
                        currVal = minMaxAlgo(new Board(board),new Board(boardCopy),true,depthToGo-1,alpha,beta);
                        extraMoves--;
                    }else{
                        currVal = minMaxAlgo(new Board(board),new Board(boardCopy),false,depthToGo - 1 ,alpha, beta);
                    }

                    if(currVal > bestValue){
                        bestValue = currVal;
                        successor = currMove;
                    }

                    alpha = Math.max(alpha,bestValue);
                    if(beta <= alpha){
                        break;
                    }

                    board.makeCopy(boardCopy);
                }
            }
            if(depthToGo == depth){
                return successor;
            }else{
                return bestValue;
            }
        }
        else{
            ArrayList<Integer> moveTracker = new ArrayList<>();
            ArrayList<Integer> moveTrackerRest = new ArrayList<>();

            for(int i=0;i<Board.NO_PITS;i++){
                if(board.pit[id][i]+i == Board.NO_PITS){
                    moveTracker.add(i);
                }else{
                    moveTrackerRest.add(i);
                }
            }

            for(int i=0; i<moveTrackerRest.size();i++){
                moveTracker.add(moveTrackerRest.get(i));
            }

            int bestValue = Integer.MAX_VALUE;

            for(int i=0;i<Board.NO_PITS;i++){
                int currMove = moveTracker.get(i);

                if(board.pit[1-id][currMove]>0){
                    int nextPlayer = board.makeMove(1-id, currMove);
                    int currVal;

                    if(nextPlayer == 1-id){
                        extraMoves--;
                        currVal = minMaxAlgo(new Board(board),new Board(boardCopy),false,depthToGo-1,alpha,beta);
                        extraMoves++;
                    }else{
                        currVal = minMaxAlgo(new Board(board),new Board(boardCopy),true,depthToGo-1,alpha,beta);
                    }

                    if(currVal<bestValue){
                        bestValue = currVal;
                    }
                    beta = Math.min(beta,bestValue);
                    if(beta<=bestValue){
                        break;
                    }
                    board.makeCopy(boardCopy);
                }
            }
            return bestValue;
        }
    }


}
