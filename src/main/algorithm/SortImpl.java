package algorithm;

/**
 * 升序排列
 */
public class SortImpl {
    /**
     *简单选择排序
     */
    public int[] SimpleSelectSort(int[] array){
        for (int i = 0; i < array.length; i++) {
            int min = i;
            for (int j = i + 1; j <array.length ; j++) {
                min = array[min] <= array[j] ? min : j;
            }
            int tmp = array[min];
            array[min] = array[i];
            array[i] = tmp;
        }
        return array;
    }

    /**
     * 简单插入排序
     */
    public int[] SimpleInsertSort(int[] array){
        for (int i = 1; i < array.length; i++) {
            int tmp = array[i];
            int j;
            for (j = i-1; j >= 0; j--) {
                if (array[j] > tmp){
                    array[j + 1] = array[j];
                }
                else {
                    break;
                }
            }
            array[j + 1] = tmp;
        }
        return array;
    }

    /**
     * 冒泡算法
     */
    public int[] BubbleSort(int[] array){
        for (int i = array.length-1; i > 0 ; i--){
            int flag = 0;
            for (int j = 0; j < i; j++) {
                if (array[j] > array[j+1]){
                    int tmp = array[j];
                    array[j] = array[j+1];
                    array[j+1] = tmp;
                    flag = 1;
                }
            }
            if (flag == 0 )
                break;
        }
        return array;
    }

    /**
     * 快速排序
     * 注：每次递归都有一个点排好序，该点就可以不用在进入递归循环中了
     */
    public int[] QuickSort(int[] array,int i, int j){
        if (i >= j){
            return null;
        }
        int tmp = array[i], l = i, r = j;
        while (l < r){
            while (l < r && array[r] >= tmp){
                r--;
            }
            if (l < r){
                array[l++] = array[r];
            }
            while (l < r && array[l] < tmp){
                l++;
            }
            if (l < r){
                array[r--] = array[l];
            }
        }
        array[l] = tmp;
        QuickSort(array, i, l-1);
        QuickSort(array, l+1, j);
        return array;
    }

    /**
     * 希尔排序（缩小增量排序）
     */
    public int[] ShellSort(int[] array){
        int length = array.length;

        for (int gap = length/2 ; gap > 0; gap /= 2){
            // 简单插入排序
            for (int i = gap; i <length; i++) {
                int tmp = array[i];
                int j;
                for (j = i-gap; j >=0; j -= gap) {
                    if (array[j] > tmp){
                        array[j+gap] = array[j];
                    }
                    else {
                        break;
                    }
                }
                array[j+gap] = tmp;
            }
        }
        return array;
    }

    /**
     * 堆排序，
     * 要求数组从1开始，即数组小标0的数不会被排序
     * 从第一个 非叶子节点开始排序
     */
    public int[] HeapSort(int[] array){
        // 初始化堆
        for (int i = (array.length -1) /2; i > 1; i--) {
            Heap(array, i, array.length-1);
        }
        //堆排序
        for (int i = array.length-1; i >1; i--) {
            int tmp = array[1];
            array[1] = array[i];
            array[i] = tmp;
            Heap(array, 1, i-1);
        }
        return array;
    }

    private void Heap(int[] ar, int head, int tail){
        int num = ar[head];
        while (head * 2 <= tail){
            int tmp = head * 2;
            if (tmp + 1 <= tail && ar[tmp+1] > ar[tmp])
                tmp += 1;
            if (ar[tmp] > num){
                ar[head] = ar[tmp];
                head = tmp;
            }
            else {
                break;
            }
        }
        ar[head] = num;
    }

    /**
     * 归并排序
     */
    public int[] MergeSort(int[] array,int low,int high){
        if (low >= high)
            return array;
        int mid = (low + high) / 2;
        MergeSort(array, low, mid);
        MergeSort(array, mid+1, high);
        int[] res = new int[array.length];
        int p = low;
        int i = low, j = mid+1;
        while (i<=mid || j<= high){
            if (i<=mid && j<= high){
                res[p++] = array[i] > array[j] ? array[j++] : array[i++];
            }
            else if (i<=mid && j>high){
                res[p++] = array[i++];
            }
            else if (j<=high && i>mid){
                res[p++] = array[j++];
            }
            else {
                break;
            }
        }
        for (int k = low; k <=high; k++) {
            array[k] = res[k];
        }
        return array;
    }

    /**
     * 计数排序
     * 要求：输入的数据必须是有确定范围的整数。
     * @param array res
     * @param min 待排序数组的最小值
     * @param max 待排序数组的最大值
     * @return
     */
    public int[] CountSort(int[] array, int min, int max){
        int[] bucket = new int[max - min + 1];
        for (int i = 0; i < array.length; i++) {
            bucket[array[i]-min]++;
        }
        int p = 0;
        for (int i = 0; i < bucket.length; i++) {
            while (bucket[i]-- > 0){
                array[p++] = i+min;
            }
        }
        return array;
    }

    /**
     *  桶排序（链表实现）
     *  能实现正数和负数的实现
     */
    public int[] BucketSort(int[] array){
        // 桶的初始化
        Listnode[] list = new Listnode[11];
        for (int i = 0; i < array.length; i++) {
            int position = array[i] < 0 ? 0 : array[i] / 10 + 1;
            if (list[position] == null)
                list[position] = new Listnode();
            Listnode node = new Listnode(array[i]);
            Listnode tmp = list[position];
            while (tmp != null){
                if (tmp.next == null || tmp.next.val >= node.val){
                    node.next = tmp.next;
                    tmp.next = node;
                    break;
                }
                else {
                    tmp = tmp.next;
                }
            }
        }
        // 排序
        int p = 0;
        for (int i = 0; i < list.length; i++) {
            if (list[i] != null){
                Listnode tmp = list[i];
                while (tmp.next != null){
                    array[p++] = tmp.next.val;
                    tmp = tmp.next;
                }
            }
        }
        return array;
    }

    /**
     * 基数排序（链式）
     * 注.仅能实现正数的排序
     *
     */
    private int[] RadixSort(int[] array){
        //1. 找到最大数的位数
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < array.length; i++) {
            if (array[i] > max)
                max = array[i];
        }
        //最大位数遍历
        for (int i = 1, mod=10, dev=1; i <= max; i*=10, dev*=10, mod*=10) {
            Listnode[] buckets = new Listnode[10];
            //存
            for (int j = 0; j < array.length; j++) {
                int index = array[j] % mod / dev;
                if (buckets[index] == null)
                    buckets[index] = new Listnode();
                Listnode tmp = buckets[index];
                Listnode node = new Listnode(array[j]);
                while (tmp != null){
                    if (tmp.next == null || tmp.next.val >= node.val){
                        node.next = tmp.next;
                        tmp.next = node;
                        break;
                    }
                    else {
                        tmp = tmp.next;
                    }
                }
            }
            //取
            int p = 0;
            for (int k = 0; k < buckets.length; k++) {
                if (buckets[k] != null){
                    Listnode tmp = buckets[k];
                    while (tmp.next != null){
                        array[p++] = tmp.next.val;
                        tmp = tmp.next;
                    }
                }
            }
        }
        return array;
    }

    class Listnode{
        int val;
        Listnode next;
        Listnode(){};
        Listnode(int val){
            this.val = val;
        }
    }

    public static void main(String[] args) {
        SortImpl sort = new SortImpl();
        int[] array={-7,-3,10,3,8,2,5,7,1,10,3,2};
//        sort.SimpleSelectSort(array);
//        sort.SimpleInsertSort(array);
//        sort.BubbleSort(array);
//        sort.QuickSort(array,0, array.length-1);
//        sort.ShellSort(array);
//        int[] array_heap={-1,10,3,8,2,5,7,1,10,3,2};
//        sort.HeapSort(array_heap);

//        int[] res = sort.MergeSort(array, 0, array.length - 1);
//        for (int re : res) {
//            System.out.print(re);
//        }

//        sort.CountSort(array, -7, 10);
//        sort.BucketSort(array);
        int[] array1 = {3,8,13,67,74,89,1,32,26,101};
        sort.RadixSort(array1);
    }
}
