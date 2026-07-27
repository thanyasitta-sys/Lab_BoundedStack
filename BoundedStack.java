/**
     * BoundedStack - ADT แทน Stack ที่ถูกกำหนดขอบเขตชัดเจน
     * 
     * ค่านามธรรม (A): ลำดับของค่าที่เรียงจากค่าที่เข้าก่อนไปค่าที่เข้าทีหลัง เช่น [1,2] หมายถึง 2 อยู่บนสุด
     * 
     * ตัวอย่างการใช้งาน: 
     *      BoundedStack s = new BoundedStack(3);
     *      s.push(1);
     *      s.push(2);
     *      System.out.println(s.peek());   // 2
     *      s.pop();
     *      System.out.println(s.peek());   // 1
     */
public class BoundedStack {

    //====representation====
    private final int capacity ;
    private final int[] data ;
    private int top ;

    // Abstraction Function:
    //   AF(capacity,data,top) =  สแตกที่มีค่า data[0]...data[top] ตามลำดับจากล่างขึ้นบน

    // Representation Invariant:
    //  capacity > 0
    //  data != null
    //  data.length == capacity
    //  -1 <= top
    //  top < capacity
 
    // Safety from rep exposure:
    //  คัดลอก object ทั้งขาเข้าและขาออก

    // เขียน checkRep()
    //  แปลง RI ทุกข้อเป็น assert 
    private void checkRep() {
        assert capacity > 0 : "capacity must be > 0" ;
        assert data != null : "data must not be null" ;
        assert data.length == capacity : "data.length must == capacity" ;
        assert -1 <= top : "-1 must be <= top " ;
        assert top < capacity : "capacity must be > top" ;
    }

    //==== Creators ====
    // สร้าง BoundedStack จากขนาดที่ให้มา
    /**
     * @param capacity ความจุสูงสุดของสแตกที่จะสร้างต้องมากกว่า 0
     * @post Stackที่สร้างขึ้นต้องว่าง size() == 0 , isEmpty ต้องเป็นจริง
     * @throws IllegalArgumentException if capacity <= 0
     * 
     */
    public BoundedStack(int capacity){
        if(capacity <= 0) throw new IllegalArgumentException("capacity must be > 0") ;
        this.capacity = capacity ;
        this.data = new int[capacity] ;
        this.top = -1 ;
        checkRep();
    }


    //==== Obsevers ====
    //คืนค่าบนสุดของStack
    /**
     * @return ค่าบนสุด
     * @throws IllegalStateException ถ้าStackว่างแล้ว
     */
    public int peek() {
        return data[top] ;
    }

    //คืนจำนวนสมาชิกปัจจุบันของStack 
    /**
     * @return จำนวนสมาชิก
     */
    public int size() {
        return top+1 ;
    }
   

    //ตรวจว่าStackว่างหรือเปล่า *isEmpty
    /**
     * @return true ถ้าไม่มีสมาชิก
     */
    public boolean isEmpty() {
        if(size() == 0)return true;
        return false;
    }

    //==== Mutators ====
    //เพิ่มค่าบนสุดในStack 
    /**
     * @param x ค่าที่เพิ่มเข้าไป
     * @pre Stackต้องยังไม่เต็ม size() < capacity
     * @post size() เพิ่มขึ้น 1, peek() คืนค่า x
     * @throws IllegalStateException ถ้าStackเต็มแล้ว size() == capacity
     */
    public int push(int x) {
        if(size()==capacity) throw new IllegalStateException();
        top++;
        data[top] = x ;
        return x ;
    }

    //ลบค่าบนสุดของStack *pop
    /**
     * @pre Stackต้องไม่ว่าง
     * @post size() ลดลง 1 
     * @return ค่าที่พึ่งถูกลบ
     * @throws IllegalStateException ถ้าStackว่างอยู่แล้ว
     */
    public int pop() {
        if(size()==0) throw new IllegalStateException();
        int poped = data[top];
        top--;
        return poped ;
    }
     

    //==== Producers ====
    //คืนStackเดิมที่มีค่าเดิมแต่reverseตำแหน่ง *reverse
    /**
     * ระวัง ห้ามแก้Stackเดิมเด็ดขาด
     * 
     * @post size() เท่าเดิม , ถ้าstack เดิมคือ [1,2] stackใหม่คือ [2,1]
     * @return Stackที่reverseแล้ว
     */

}