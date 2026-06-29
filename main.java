#include <iostream>
#include <iomanip>
#include <string>
using namespace std;

struct Node{
    int docID;
    float score;
    Node* left;
    Node* right;
};
class BST{
    Node* root;

    Node* createNode(int id, float sc)
    {
        Node *p = new Node;
        p->docID = id;
        p->score = sc;
        p->left = nullptr;
        p->right = nullptr;
        return p;
    }

    bool insertNode(Node *&r, int id, float sc){
        if(r == NULL){
        r = createNode(id, sc);
        return true;
        }

        if(id == r->docID){
        r->score = sc;
        return false;
        }
        else if(id < r->docID)
        return insertNode(r->left, id, sc);
        else
        return insertNode(r->right, id, sc);
        }

    bool searchNode(Node *r, int id, float &sc){
        if(r == NULL)
        return false;

        if(id == r->docID)
        {
        sc = r->score;
        return true;
        }
        else if(id < r->docID)
        return searchNode(r->left, id, sc);
        else
        return searchNode(r->right, id, sc);
        }

    Node* findMin(Node *r){
        while(r != NULL && r->left != NULL)
        r = r->left;

        return r;
        }

    bool removeNode(Node *&r, int id){
        if(r == NULL)
        return false;

        if(id < r->docID)
            return removeNode(r->left, id);
        else if(id > r->docID)
            return removeNode(r->right, id);
        else{
            Node *temp;

            if(r->left == NULL && r->right == NULL){
            delete r;
            r = NULL;
        }
        else if(r->left == NULL){
            temp = r;
            r = r->right;
            delete temp;
        }
        else if(r->right == NULL){
            temp = r;
            r = r->left;
            delete temp;
        }
        else{
            Node *minNode = findMin(r->right);
            r->docID = minNode->docID;
            r->score = minNode->score;
            removeNode(r->right, minNode->docID);
    }


        return true;
        }
    }
     void rangeNode(Node *r, int low, int high, int arr[], int &count){
        if(r == NULL)
        return;

        if(r->docID > low)
            rangeNode(r->left, low, high, arr, count);

        if(r->docID >= low && r->docID <= high){
            arr[count] = r->docID;
            count++;
        }

        if(r->docID < high)
            rangeNode(r->right, low, high, arr, count);
    }

    void storeNodes(Node *r, int ids[], float scores[], int &count){
        if(r == NULL)
            return;

        storeNodes(r->left, ids, scores, count);

        ids[count] = r->docID;
        scores[count] = r->score;
        count++;

        storeNodes(r->right, ids, scores, count);
        }
    int heightNode(Node *r){
        if(r == NULL)
            return 0;


        int leftHeight = heightNode(r->left);
        int rightHeight = heightNode(r->right);

        if(leftHeight > rightHeight)
            return leftHeight + 1;
        else
            return rightHeight + 1;
        }
    void clearNode(Node *&r){
    if(r == NULL)
        return;

    clearNode(r->left);
    clearNode(r->right);

    delete r;
    r = NULL;
    }

public:
    BST(){
        root = NULL;
    }
    bool insertDoc(int id, float sc){
        return insertNode(root, id, sc);
    }
    bool removeDoc(int id){
    return removeNode(root, id);
    }
    bool findDoc(int id, float &sc){
    return searchNode(root, id, sc);
    }

    void rangeQuery(int low, int high)
    {
        int arr[1000];
        int count = 0;

        rangeNode(root, low, high, arr, count);

        if(count == 0)
            cout << "Empty";
        else{
            for(int i = 0; i < count; i++){
        cout << arr[i];
        if(i != count - 1)
        cout << " ";
        }
        }
        cout << endl;
    }

    void topK(int k)
    {
    int ids[1000];
    float scores[1000];
    int count = 0;

    storeNodes(root, ids, scores, count);

    if(count == 0 || k <= 0)
    {
        cout << "Empty" << endl;
        return;
    }

    for(int i = 0; i < count - 1; i++){
        for(int j = i + 1; j < count; j++){
            if(scores[j] > scores[i] || (scores[j] == scores[i] && ids[j] < ids[i])){
                float tempScore = scores[i];
                scores[i] = scores[j];
                scores[j] = tempScore;

                int tempID = ids[i];
                ids[i] = ids[j];
                ids[j] = tempID;
                }
        }
    }

    int limit;
    if(k < count)
        limit = k;
    else
        limit = count;

    for(int i = 0; i < limit; i++){
        cout << ids[i];
        if(i != limit - 1)
        cout << " ";
    }
    cout << endl;
    }

    int height()
    {
    return heightNode(root);
    }

    void clear()
    {
    clearNode(root);
    }
    };

    int main()
    {
        cout << "Commands list"<< endl;
        cout << " INSERT\n REMOVE\n RANGE\n TOPK\n HEIGHT\n CLEAR\n EXIT" << endl;

        BST bst;
        string com;

        int id, low, high, k;
        float score;

        while(true){
            cin >> com;
            if(com == "INSERT"){
                cin >> id;

                cin  >> score;

            if(bst.insertDoc(id, score))
                cout << "Inserted" << endl;

            else
                cout << "Updated" << endl;
            }


            else if(com == "REMOVE"){
                cin >> id;

            if(bst.removeDoc(id))
                cout << "Removed" << endl;
            else
                cout << "NotFound" << endl;
            }


            else if(com == "FIND"){
                cin >> id;

            if(bst.findDoc(id, score))
                cout << score << endl;
            else
                cout << "NotFound" << endl;
            }


            else if(com == "RANGE"){
                cin >> low >> high;
                bst.rangeQuery(low, high);
            }


            else if(com == "TOPK"){
                cin >> k;
                bst.topK(k);
            }


            else if(com == "HEIGHT"){
                cout << bst.height() << endl;
            }


            else if(com == "CLEAR"){
                bst.clear();
                cout << "Cleared" << endl;
            }

            else if (com == "EXIT"){
                return 0;
            }
        }

        return 0;
};
