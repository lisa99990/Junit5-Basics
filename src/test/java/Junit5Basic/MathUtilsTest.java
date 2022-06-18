package Junit5Basic;

import jdk.jfr.Enabled;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class MathUtilsTest {
    MathUtils mathUtils;
    TestInfo testInfo;
    TestReporter testReporter;

    @BeforeAll // this method is called before the MathUtilsTest class is instanctiated.
    // SO, make it static. but if we use @TestInstance(TestInstance.Lifecycle.PER_CLASS),
    //we do not need static bcz only one instance of testInstance is created
     static void beforeAllInit(){
        System.out.println("Before all it is instantiated");
    }

    @AfterAll
    static void afterAll(){
        System.out.println("After all it is instantiated");
    }

    @BeforeEach // before each method call, this method is called
    void init(TestInfo testInfo, TestReporter testReporter){
        this.testInfo = testInfo;
        this.testReporter = testReporter;
        mathUtils = new MathUtils();

    }

    @AfterEach
    void cleanUp(){
        System.out.println("Cleaning Up");
    }

    @Test
    @DisplayName("Adding two Numbers")
    @EnabledOnOs(OS.LINUX)
    void add() {
        int expected = 2;
        int actual = mathUtils.add(1,1);
        assertEquals(expected, actual,"The sum should be");
    }

    @Test
    @Disabled
    void testComputeCircleArea(){

        assertEquals(314.1592653589793, mathUtils.computeCircleArea(10),"Should return right crcle area");
    }

    @Test
    @DisplayName("Add if Server running")
    void serverDownTest(){
        //some logic to check server running or not and get output in boolean
        boolean isServerRunning = true;
        assumeTrue(isServerRunning);
        if(isServerRunning){
            assertEquals(3,mathUtils.getAdditionIfServerUp(2,1) );
        }
        }

    @Test
    void testDivideByZero(){
        assertThrows(ArithmeticException.class , () -> mathUtils.divide(1,0),"Different exception captured");
    }

    @Test
    @DisplayName("Multiply Method")
    void testMultiply(){
        assertAll(
                ()->assertEquals(3,mathUtils.multiply(3,1)),
                ()->assertEquals(4, mathUtils.multiply(2,2)),
                ()->assertNotEquals(6,mathUtils.multiply(2,4))
                );
        this.testReporter.publishEntry("Reported display name "+ testInfo.getDisplayName());
    }

    @Nested
    class substractClass{

        @Test
        @DisplayName("For positive numbers")
        void substractPositive(){
            assertEquals(2, mathUtils.substract(5,3),"Substraction of two positive numbers Error");
        }

        @RepeatedTest(3)
        @DisplayName("For negative numbers")
        @Tag("Tagged")
        void substractNegative(){
            assertEquals(-2, mathUtils.substract(-5,-3),"Substraction of two negative numbers Error");
        }
    }
}