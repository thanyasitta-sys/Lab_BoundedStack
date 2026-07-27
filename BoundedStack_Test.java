public class BoundedStack_Test {
    private static int pass=0 , fail=0 ;

    // helper กลาง — พิมพ์ PASS/FAIL และนับผลให้เอง
    private static void check(String name , boolean ok) {
        if(ok) {
            pass++;
            System.out.println("[PASS]"+name);
        }else {
            fail++;
            System.out.println("[FAIL]"+name);
        }
    }

    public static void main(String[] args) {
        boolean ea = false;
        assert ea = true; //วิธีเช็คว่าตอนรันโปรแกรมนี้ เปิด -ea flag ไว้หรือเปล่า อาศัยธรรมชาติของ assert ที่จะถูกข้ามไปเฉยๆ ถ้าไม่เปิด flag
        if(!ea) {
            System.out.println("Assertions are OFF: run with -ea");
        }

        System.out.println("=== BoundedStack Test ===\n");

        testCreators();
        testPush();
        testPop();
        testObservers();
        testProducer();
        testExposure();

        System.out.println("\n=== Summary ===");
        System.out.println("Passed: " + pass);
        System.out.println("Failed: " + fail);
        System.out.println("Total : " + (pass + fail));
        System.out.println(fail == 0 ? "ALL TESTS PASSED" : "SOME TESTS FAILED");
        System.exit(fail == 0 ? 0 : 1); //เช็คผลเทสต์แล้วสั่งปิดโปรแกรมด้วยรหัส `0` (สำเร็จ) หรือ `1` (พัง) เพื่อส่งสัญญาณบอกระบบภายนอกหรือ CI/CD ว่าการทดสอบผ่านหรือล้มเหลว
    }
    private static void testCreators(){
        System.out.println("-- testCreator --");

        boolean threwcreat_1 = false;
        try {
            BoundedStack bounded1 = new BoundedStack(-1);
        } catch (IllegalArgumentException e) {
            threwcreat_1 = true;
        }
        check("capacity=-1 -> throw IllegalArgumentException", threwcreat_1);

        boolean threwcreat_2 = false;
        try {
            BoundedStack bounded2 = new BoundedStack(0);
        } catch (IllegalArgumentException e) {
            threwcreat_2 = true;
        }
        check("capacity=0 -> throw IllegalArgumentException", threwcreat_2);

        BoundedStack bounded3 = new BoundedStack(1);
        check("capacity=1 -> isEmpty==true",bounded3.isEmpty());
        
        BoundedStack bounded4 = new BoundedStack(3);
        check("capacity=3 -> isEmpty==true", bounded4.isEmpty());
    }

    private static void testObservers(){
        BoundedStack bounded = new BoundedStack(2);
        check("isEmpty() ว่าง -> true ",bounded.isEmpty()); 
        check("size() ตอนสแตกว่างเปล่า -> ได้ 0", bounded.size() == 0);
         
        boolean threwpeek = false;
        try {
            bounded.peek();
        } catch (IllegalStateException e) {
            threwpeek = true;
        }
        check("peek() ตอนข้อมูลว่าง -> throw IllegalStateException",threwpeek);

        bounded.push(2);bounded.push(3);

        check("size() เต็ม == capacity", bounded.size() == 2 );
        check("isEmpty() มีข้อมูล -> false", !bounded.isEmpty());
        check("peek() ข้อมูลหลายตัว -> ได้ตัวบนสุด(3)", bounded.peek() == 3);
        check("peek() ซ้ำๆ -> ค่าไม่เปลี่ยน(เรียกอีกรอบ)", bounded.peek() == 3);
    }
    private static void testPush(){
        BoundedStack bounded = new BoundedStack(2);
        bounded.push(12); int before = bounded.size();
        bounded.push(88); int after = bounded.size();

        check("push() -> size()ต้องเพิ่ม", after>before);
        check("push() -> peek()แล้วต้องเจอตัวล่าสุดที่push", bounded.peek()==88);
        check("push()ข้อมูลตัวสุดท้าย -> size() == capacity", bounded.size()==2);

        boolean threwpush = false;
        try {
            bounded.push(15);
        } catch (IllegalStateException e) {
            threwpush = true;
        }
        check("push() เมื่อข้อมูลเต็ม -> throw IllegalStateException", threwpush);
    }
    private static void testPop(){
        BoundedStack bounded = new BoundedStack(2);

        bounded.push(12);bounded.push(21);
        int before = bounded.size();
        bounded.pop(); int after = bounded.size();

        check("pop() -> getSize()ต้องลด", before>after);
        check("pop() -> peek()ต้องไม่เจอตัวที่pop", bounded.peek()==12);

        bounded.pop();
        boolean threwpop = false;
        try {
            bounded.pop();
        } catch (IllegalStateException e) {
            threwpop = true;
        }
        check("pop() ตอนข้อมูลว่าง -> throw IllegalStateException", threwpop);
    }
    private static void testProducer(){
        BoundedStack bounded = new BoundedStack(2);
        bounded.push(15);
        bounded.push(30);
        BoundedStack reversed = bounded.reverse();

        check("reverse() -> getSize() เท่าเดิม", reversed.size() == bounded.size());
        check("reverse() -> peek() แรกต้องเป็นตัวล่างสุดของอันเดิม",reversed.peek()==15);
        reversed.pop();
        check("reverse() -> pop() แล้ว peek() ต้องเป็นตัวบนสุดของอันเดิม", reversed.peek() == 30);
        
        check("reverse() -> ต้นฉบับ size ไม่เปลี่ยน", bounded.size() == 2);
        check("reverse() -> ต้นฉบับ peek ไม่เปลี่ยน", bounded.peek() == 30);

        BoundedStack empty = new BoundedStack(2);
        BoundedStack emptyReversed = empty.reverse();
        
        check("reverse() stackว่าง -> ได้stackว่างคืน",emptyReversed.isEmpty());

        BoundedStack one = new BoundedStack(2);
        one.push(75);
        BoundedStack oneReversed = one.reverse();

        check("reverse() -> size ไม่เปลี่ยน", oneReversed.size() == 1);
        check("reverse() -> peek ไม่เปลี่ยน", oneReversed.peek() == 75);

    }
    private static void testExposure(){
        BoundedStack original = new BoundedStack(6);
        original.push(24);
        original.push(66);
        int peekOriginal = original.peek();
        BoundedStack reversed = original.reverse();
        reversed.push(99);
        check("reverse() ใช้ array คนละอันกับต้นฉบับ", peekOriginal == original.peek());

    }

}
