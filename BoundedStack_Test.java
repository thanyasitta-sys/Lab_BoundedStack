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
        } catch (Exception e) {
            threwcreat_1 = true;
        }
        check("capacity=-1 -> throw", threwcreat_1);

        boolean threwcreat_2 = false;
        try {
            BoundedStack bounded2 = new BoundedStack(0);
        } catch (Exception e) {
            threwcreat_2 = true;
        }
        check("capacity=0 -> throw", threwcreat_2);

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
        } catch (Exception e) {
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
        } catch (Exception e) {
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
        } catch (Exception e) {
            threwpop = true;
        }
        check("pop() ตอนข้อมูลว่าง -> throw IllegalStateException", threwpop);
    }
    private static void testProducer(){
        // R19: reverse() -> getSize() เท่าเดิม
        // R20: reverse() -> ค่าภายในเป็นตัวเดิมที่สลับตำแหน่ง
        // R21: reverse() -> ต้นฉบับ ไม่ถูกแก้เลย
        // R23: reverse() stackว่าง -> ได้stackว่างคืน 
        // R24: reverse() stackที่มีข้อมูลตัวเดียว -> ได้stackที่มีข้อมูลตัวเดียวคืน
    }
    private static void testExposure(){
        // R25: reverse() ใช้ array คนละอันกับต้นฉบับ
    }

}
