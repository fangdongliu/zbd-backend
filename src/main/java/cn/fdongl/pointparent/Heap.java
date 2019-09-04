package cn.fdongl.pointparent;

public class Heap <E>{
    E[] heap;
    int size = 0;
    int capacity = 0;

    void heapify(){
        for(int i = (size << 1);i>=0;i--){
            siftDown(i);
        }
    }

    void siftUp(){

    }

    void siftDown(int i){
        int parent;
        while(i<size){

        }
    }

}
