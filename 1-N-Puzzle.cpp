#include <iostream>
#include <vector> 
#include <queue>
#include <stack>
#include<unordered_map>
#include<unordered_set>
#include<functional>

#define RIGHT 0
#define LEFT 1
#define UP 2
#define DOWN 3
#define HUMMING 10
#define MANHATTEN 11

int k;
int numOfMoves=0;
long long explored=0;
long long expanded=0;

using namespace std;
class node{
    public:

        int g=0;
        int h=0;

        int f=0;
        bool isTraversed=false;
        int lastMove=-1;

        vector<node*> children;

        node *parent=NULL;

        vector<vector<int>> board;
        pair<int,int> blankCoOrd;

        node(vector<vector<int>> vec){
            board=vec;
        }

        node(const node &n){
            this->g=n.g;
            this->h=n.h;
            this->f=n.f;

            this->children=n.children;

            this->parent = n.parent;
            
            this->board = n.board;
        }

        node(vector<vector<int>> vec, int g){
            this->board= vec;
            this->g=g;
        }

        void printBoard(){
            for( int i=0;i<k;i++){
                for(int j=0;j<k;j++){
                   /* if(board[i][j]==0)
                        cout<<'*'<<" ";
                    else
                        cout<<board[i][j]<<" ";
                        */
                    cout<<board[i][j]<<" ";
                }
                cout<<endl;
            }
        }

        void operator=(const node &n){
            board=n.board;
            h=n.h;
            g=n.g;
            f=n.f;

            children=n.children;
            parent=n.parent;
        }
};
struct comparison{
    bool operator()(const node* nodeOne, const node *nodeTwo){
        if(nodeOne->f > nodeTwo->f)
            return true;
        else 
            return false;
    }
};
priority_queue<node*,vector<node*>,comparison> pq;
//vector<node*> traversedNode;

struct BoardHash {
    size_t operator()(const vector<vector<int>>& board) const {
        size_t seed = board.size();
        for (const auto& row : board) {
            for (const auto& val : row) {
                seed ^= val + 0x9e3779b9 + (seed << 6) + (seed >> 2);
            }
        }
        return seed;
    }
};

struct BoardEqual {
    bool operator()(const vector<vector<int>>& lhs, const vector<vector<int>>& rhs) const {
        return lhs == rhs;
    }
};

unordered_set<vector<vector<int>>, BoardHash, BoardEqual> traversedNode;


vector<vector<int>> shiftingPosition(int a,int b,int i,int j,vector<vector<int>> board){
    int temp = board[i][j];
    board[i][j]=board[a][b];
    board[a][b]=temp;
    return board;
}

pair<int,int> findBlank(vector<vector<int>> board){
    for(int i=0;i<k;i++){
        for(int j=0;j<k;j++){
            if(board[i][j]==0){
                pair coOrd(i,j);
                return coOrd;
            }
        }
    }

}
/*
bool alreadyTraversed(node* n){
    for(int i=0;i<traversedNode.size();i++){
        if(n->board==traversedNode[i]->board){
            return true;
        }
    }
    return false;
   
}
*/

bool alreadyTraversed(const node* n) {
    return traversedNode.count(n->board) > 0;
}

vector<vector<int>> shift(vector<vector<int>> board, int shiftCode,pair<int,int> bc){

    //pair<int,int> blankCoOrd = findBlank(board);
    pair<int,int> blankCoOrd = bc;

    if(shiftCode==UP){
        if(blankCoOrd.first==0)
            return board;
        return shiftingPosition(blankCoOrd.first,blankCoOrd.second,blankCoOrd.first-1,blankCoOrd.second,board);

    } else if(shiftCode==DOWN){
        if(blankCoOrd.first==k-1)
            return board;
        
         return shiftingPosition(blankCoOrd.first,blankCoOrd.second,blankCoOrd.first+1,blankCoOrd.second,board);


    } else if(shiftCode==RIGHT){
        if(blankCoOrd.second==k-1) return board;

         return shiftingPosition(blankCoOrd.first,blankCoOrd.second,blankCoOrd.first,blankCoOrd.second+1,board);

    } else if(shiftCode==LEFT){
        if(blankCoOrd.second==0)return board;

         return shiftingPosition(blankCoOrd.first,blankCoOrd.second,blankCoOrd.first,blankCoOrd.second-1,board);

    }
} 

int hummingDistance(vector<vector<int>> vec){
    int value=0;
    //vector<vector<int>> targetVector={{1,2,3},{4,5,6},{7,8,0}};
    
    vector<vector<int>> targetVector;
    for(int i=0;i<k;i++){
        vector<int> vecvec;
        targetVector.push_back(vecvec);
    }
    int temp=1;
    for(int i=0;i<k;i++){
        for(int j=0;j<k;j++){
            targetVector[i].push_back(temp++);
        }
    }
    targetVector[k-1][k-1]=0;

    for(int i=0;i<k;i++){
        for(int j=0;j<k;j++){
            if(vec[i][j]==0) continue;

            if(vec[i][j]!=targetVector[i][j]) value++;
        }
    }
    return value;
}

int findManDistance(vector<vector<int>> vec){
    unordered_map<int,pair<int,int>> mp;

    int count=1;
    for(int i=0;i<k;i++){
        for(int j=0;j<k;j++){
            mp[count]=make_pair(i,j);
            count++;
        }
    }
    mp[0]=make_pair(k-1,k-1);

    int result=0;
    for(int i=0;i<k;i++){
        for(int j=0;j<k;j++){
            int count=vec[i][j];
            if(count==0) continue;
            result+= abs(i-mp[count].first)+abs(j-mp[count].second);
        }
    }
    return result;
    
}

void printAllNodes(node* n){
    stack<node*> stackOfNodes;
    stackOfNodes.push(n);

    while(n->parent!=NULL){
        n=n->parent;
        stackOfNodes.push(n);
    }

    cout<<"printing out all the states"<<endl;
    
    cout<<"Minimum Moves :"<<stackOfNodes.size()-1<<endl;
    cout<<"Explored nodes: "<<explored<<endl;
    cout<<"Expanded nodes: "<<expanded<<endl;
    
    while(stackOfNodes.size()>0){
        stackOfNodes.top()->printBoard();
        cout<<endl;
        stackOfNodes.pop();
    }
}


void aStarSearch(node* startNode,int choice){
    //cout<<"a star e dhuksi"<<endl;
    int nodesInQueue=0;
    startNode->blankCoOrd = findBlank(startNode->board);
    explored++;
    pq.push(startNode);
    //vector<vector<int>> targetVector = {{1,2,3},{4,5,6},{7,8,0}};

    vector<vector<int>> targetVector;
    for(int i=0;i<k;i++){
        vector<int> vecvec;
        targetVector.push_back(vecvec);
    }
    int temp=1;
    for(int i=0;i<k;i++){
        for(int j=0;j<k;j++){
            targetVector[i].push_back(temp++);
        }
    }
    targetVector[k-1][k-1]=0;
    


    while(pq.size()>0){
        
        node* n=new node(*pq.top());
        expanded++;
        n->blankCoOrd=pq.top()->blankCoOrd;
       // cout<<"value :"<<n->f<<" "<<n->h<<" "<<n->g<<endl;

        if(targetVector==n->board){
            cout<<"Target Found"<<endl;
            printAllNodes(n);
            break;
        }
        else{
            //traversedNode.push_back(pq.top());
            traversedNode.insert(pq.top()->board);
            //cout<<"Exploring..."<<endl;
            //pq.top()->printBoard();
    
            pq.pop();
        }

        vector<node*> createdChild;

        for(int i=0;i<4;i++){
            node* temp=new node(shift(n->board,i,n->blankCoOrd),((n->g)+1));
            temp->blankCoOrd=n->blankCoOrd;
            temp->lastMove=i;
            //cout<<temp->blankCoOrd.first<<" "<<temp->blankCoOrd.second<<endl;            
           
            

            if(i==UP){
                if(temp->blankCoOrd.first!=0)
                    temp->blankCoOrd.first--;

            } else if(i==DOWN){
                if(temp->blankCoOrd.first!=k-1)
                    temp->blankCoOrd.first++;

            } else if(i==RIGHT){
                if(temp->blankCoOrd.second!=k-1) 
                    temp->blankCoOrd.second++;
               

            } else if(i==LEFT){
                if(temp->blankCoOrd.second!=0)
                    temp->blankCoOrd.second--;

             }

            

            
            if(choice==MANHATTEN)
                {
                    temp->h=findManDistance(temp->board);
                }
            else if(choice==HUMMING){
                temp->h=hummingDistance(temp->board);
            }

            temp->f=temp->h+temp->g;

            if(temp->lastMove==RIGHT && n->lastMove==LEFT || temp->lastMove==LEFT && n->lastMove==RIGHT || temp->lastMove==UP && n->lastMove==DOWN || temp->lastMove==DOWN && n->lastMove==UP)
                continue;
            createdChild.push_back(temp);
        }

        for(int i=0;i<4;i++){
            if(alreadyTraversed(createdChild[i])==false){
                //cout<<"vc_"<<createdChild[i]->f<<endl;
                node* t=createdChild[i];
                t->parent=n;
                n->children.push_back(t);
                explored++;
                pq.push(t);
            }
        }
    }

}
bool isSolvable(node* n){
    int invCount=0;

    for(int i=0;i<k*k-1;i++){
        for(int j=i+1;j<k*k;j++){
            int xi=i/k;
            int yi=i%k;
            int xj=j/k;
            int yj=j%k;
            if(n->board[xi][yi] && n->board[xj][yj] &&  n->board[xi][yi]>n->board[xj][yj]){
                invCount++;
            }
        }
    }
    pair<int,int> pos = findBlank(n->board);
    cout<<"inversion Count: "<<invCount<<endl;
    if(k%2!=0){
        if(invCount%2==0) 
            return true;
        else
            return false;
    }
    else{
        // int posInt=pos.first*k+pos.second;
        // if(posInt%2!=0 && invCount%2==0)
        //     return true;
        // else
        //     return false;
        int temp=invCount;
        if(temp%2!=0 && pos.first%2==0 || temp%2==0 && pos.first%2!=0)
            return true;
        else 
            return false;
    } 
}
int main(){
    cout<<"Enter grid size (k) :";
    cin>>k;
    while(k<=2){
        cout<<"The value of k should be greater than 2 ."<<endl;
        cin>>k;
    }

    //cout<<"enter starting values...."<<endl;
    vector<vector<int>> puzzleBoard(k,vector<int>(k));

    for(int i=0;i<k;i++){
        for(int j=0;j<k;j++){
            int x=0;
            cin>>x;
            //if(x=='*')
                //puzzleBoard[i][j]=0;
            //else 
                //puzzleBoard[i][j]=x-'0';
            puzzleBoard[i][j]=x;
        }
    }

    cout<<"the values that you have entered is shown below: "<<endl;
    node startNode(puzzleBoard,0);
    startNode.printBoard();

    //cout<<"Solvable: "<<isSolvable(&startNode)<<endl;
    //isSolvable(&startNode)
    if(isSolvable(&startNode)){
        cout<<"Solvable: True"<<endl;
        //cout<<"Using Humming Distance....."<<endl;
        //aStarSearch(&startNode,HUMMING);
        cout<<"Using Manhatten distance....";
        aStarSearch(&startNode,MANHATTEN);
    }else{
        cout<<"Solvable: False"<<endl;
    }
    

    cout<<endl;
    cout<<"Finish"<<endl;

}