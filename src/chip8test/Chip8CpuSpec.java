package chip8test;


import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

import chip8main.Chip8Cpu;
import org.junit.Test;


public class Chip8CpuSpec {

    @Test
    public void emulateCycle() {
    }

    @Test
    public void writeToMemTest() {
        Chip8Cpu cpu = new Chip8Cpu();

        cpu.writeToMem(0, 0x60);
        cpu.writeToMem(1, 0x11);

        String out = cpu.dumpMemory();

        assertTrue(out.startsWith(Integer.toString(0x60) + " " + Integer.toString(0x11)));
    }

    //Tests instruction 0x00E0, which clears the screen
    @Test
    public void clearScreenTest()
    {
        Chip8Cpu cpu = new Chip8Cpu();

        //Place sprite at memory location 0
        cpu.writeToMem(0, 0x3C);
        cpu.writeToMem(1, 0xC3);
        cpu.writeToMem(2, 0xFF);

        //Sets I at mem address 0x200 (program start)
        cpu.writeToMem(0x200, 0xA0);
        cpu.writeToMem(0x201, 0x00);


        //Place draw instruction at mem address 0x202
        cpu.writeToMem(0x202, 0xD0);
        cpu.writeToMem(0x203, 0x03);

        //Place instruction to clear screen at mem address 0x204
        cpu.writeToMem(0x204, 0x00);
        cpu.writeToMem(0x205, 0xE0);

        cpu.emulateCycle();
        assertTrue(cpu.getI() == 0);

        cpu.emulateCycle();
        String out = cpu.printGraphics();
        assertTrue(!out.equals("0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n"));

        cpu.emulateCycle();
        out = cpu.printGraphics();
        assertTrue(out.equals("0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n"));
        assertEquals(0x206, cpu.getPC());
    }

    //Tests instructions 0x00EE and 0x2NNN, calling a subroutine and returning from it
    @Test
    public void callAndReturnTest()
    {
        Chip8Cpu cpu = new Chip8Cpu();

        //Writes 0x2500, jumping to address 500
        cpu.writeToMem(0x200, 0x25);
        cpu.writeToMem(0x201, 0x00);

        cpu.writeToMem(0x500, 0x00);
        cpu.writeToMem(0x501, 0xEE);

        cpu.emulateCycle();
        assertEquals(0x500, cpu.getPC());

        cpu.emulateCycle();
        assertEquals(0x202, cpu.getPC());

    }

    //Tests instruction 0x1NNN, jumping to another address
    @Test
    public void jumpTest()
    {
        Chip8Cpu cpu = new Chip8Cpu();

        //Writes 0x1500, jumping to address 500
        cpu.writeToMem(0x200, 0x25);
        cpu.writeToMem(0x201, 0x00);

        cpu.emulateCycle();
        assertEquals(0x500, cpu.getPC());
    }

    //Tests instruction 0x3XNN, which skips the next instruction if VX = NN
    @Test
    public void reqEqualTest()
    {
        Chip8Cpu cpu = new Chip8Cpu();

        //Writes 0x6051, which sets V0 to 51
        cpu.writeToMem(0x200, 0x60);
        cpu.writeToMem(0x201, 0x51);

        //Writes 0x3051, which skips the next instruction
        cpu.writeToMem(0x202, 0x30);
        cpu.writeToMem(0x203, 0x51);

        cpu.emulateCycle();
        assertEquals(0x51, cpu.getV(0));
        assertEquals(0x202, cpu.getPC());

        cpu.emulateCycle();
        assertEquals(0x206, cpu.getPC());
    }

    //Tests instruction 0x3XNN failure, which skips the next instruction if VX = NN
    @Test
    public void reqEqualFailTest()
    {
        Chip8Cpu cpu = new Chip8Cpu();

        //Writes 0x6051, which sets V0 to 51
        cpu.writeToMem(0x200, 0x60);
        cpu.writeToMem(0x201, 0x51);

        //Writes 0x3052, which will not skip the next instruction
        cpu.writeToMem(0x202, 0x30);
        cpu.writeToMem(0x203, 0x52);

        cpu.emulateCycle();
        assertEquals(0x51, cpu.getV(0));
        assertEquals(0x202, cpu.getPC());

        cpu.emulateCycle();
        assertEquals(0x204, cpu.getPC());
    }

    //Tests instruction 0x4XNN, which skips the next instruction if VX != NN
    @Test
    public void reqNotEqualTest()
    {
        Chip8Cpu cpu = new Chip8Cpu();

        //Writes 0x6051, which sets V0 to 51
        cpu.writeToMem(0x200, 0x60);
        cpu.writeToMem(0x201, 0x51);

        //Writes 0x4052, which skips the next instruction
        cpu.writeToMem(0x202, 0x40);
        cpu.writeToMem(0x203, 0x52);

        cpu.emulateCycle();
        assertEquals(0x51, cpu.getV(0));
        assertEquals(0x202, cpu.getPC());

        cpu.emulateCycle();
        assertEquals(0x206, cpu.getPC());
    }

    //Tests instruction 0x4XNN failure, which skips the next instruction if VX != NN
    @Test
    public void reqNotEqualFailureTest()
    {
        Chip8Cpu cpu = new Chip8Cpu();

        //Writes 0x6051, which sets V0 to 51
        cpu.writeToMem(0x200, 0x60);
        cpu.writeToMem(0x201, 0x51);

        //Writes 0x4052, which skips the next instruction
        cpu.writeToMem(0x202, 0x40);
        cpu.writeToMem(0x203, 0x51);

        cpu.emulateCycle();
        assertEquals(0x51, cpu.getV(0));
        assertEquals(0x202, cpu.getPC());

        cpu.emulateCycle();
        assertEquals(0x204, cpu.getPC());
    }

    //Tests instruction 0x5XY0, which skips the next instruction if VX = VY
    @Test
    public void reqEqualRegTest()
    {
        Chip8Cpu cpu = new Chip8Cpu();

        //Writes 0x6051, which sets V0 to 51
        cpu.writeToMem(0x200, 0x60);
        cpu.writeToMem(0x201, 0x51);

        //Set V1 to 51
        cpu.writeToMem(0x202, 0x61);
        cpu.writeToMem(0x203, 0x51);

        //Writes 0x3051, which skips the next instruction
        cpu.writeToMem(0x204, 0x50);
        cpu.writeToMem(0x205, 0x10);

        cpu.emulateCycle();
        assertEquals(0x51, cpu.getV(0));
        assertEquals(0x202, cpu.getPC());

        cpu.emulateCycle();
        assertEquals(0x51, cpu.getV(1));
        assertEquals(0x204, cpu.getPC());

        cpu.emulateCycle();
        assertEquals(0x208, cpu.getPC());
    }

    //Tests instruction 0x5XY0, which skips the next instruction if VX = VY
    @Test
    public void reqEqualRegFailureTest()
    {
        Chip8Cpu cpu = new Chip8Cpu();

        //Writes 0x6051, which sets V0 to 51
        cpu.writeToMem(0x200, 0x60);
        cpu.writeToMem(0x201, 0x51);

        //Set V1 to 51
        cpu.writeToMem(0x202, 0x61);
        cpu.writeToMem(0x203, 0x52);

        //Writes 0x3051, which skips the next instruction
        cpu.writeToMem(0x204, 0x50);
        cpu.writeToMem(0x205, 0x10);

        cpu.emulateCycle();
        assertEquals(0x51, cpu.getV(0));
        assertEquals(0x202, cpu.getPC());

        cpu.emulateCycle();
        assertEquals(0x52, cpu.getV(1));
        assertEquals(0x204, cpu.getPC());

        cpu.emulateCycle();
        assertEquals(0x206, cpu.getPC());
    }

    //Tests instruction 0x6XNN, which sets register VX to NN
    @Test
    public void setRegTest() {
        Chip8Cpu cpu = new Chip8Cpu();

        //Writes 0x6051, which sets V0 to 51
        cpu.writeToMem(0x200, 0x60);
        cpu.writeToMem(0x201, 0x51);

        cpu.emulateCycle();
        assertEquals(0x51, cpu.getV(0));
        assertEquals(0x202, cpu.getPC());
    }

    //Tests instruction 0x7XNN, which adds NN to register VX
    @Test
    public void addToRegTest()
    {
        Chip8Cpu cpu = new Chip8Cpu();

        //Writes 0x6051, which sets V0 to 51
        cpu.writeToMem(0x200, 0x60);
        cpu.writeToMem(0x201, 0x51);

        //Writes 0x7002, which adds 02 to V0
        cpu.writeToMem(0x202, 0x70);
        cpu.writeToMem(0x203, 0x02);

        cpu.emulateCycle();
        assertEquals(0x51, cpu.getV(0));
        assertEquals(0x202, cpu.getPC());

        cpu.emulateCycle();
        assertEquals(0x53, cpu.getV(0));
        assertEquals(0x204, cpu.getPC());
    }

    //Tests instruction 0x8XY0, which sets VX to VY
    @Test
    public void setRegToRegTest()
    {
        Chip8Cpu cpu = new Chip8Cpu();

        //Writes 0x6051, which sets V0 to 51
        cpu.writeToMem(0x200, 0x60);
        cpu.writeToMem(0x201, 0x51);

        //Set V1 to 51
        cpu.writeToMem(0x202, 0x61);
        cpu.writeToMem(0x203, 0x52);

        //Writes 0x8010, which sets VX to VY
        cpu.writeToMem(0x204, 0x80);
        cpu.writeToMem(0x205, 0x10);

        cpu.emulateCycle();
        assertEquals(0x51, cpu.getV(0));
        assertEquals(0x202, cpu.getPC());

        cpu.emulateCycle();
        assertEquals(0x52, cpu.getV(1));
        assertEquals(0x204, cpu.getPC());

        cpu.emulateCycle();
        assertEquals(0x206, cpu.getPC());
        assertEquals(0x52, cpu.getV(0));
        assertEquals(0x52, cpu.getV(1));
    }

    //Tests instruction 0x8XY1, Sets VX to VX | VY
    @Test
    public void regOrTest()
    {
        Chip8Cpu cpu = new Chip8Cpu();

        //Writes 0x6051, which sets V0 to 51
        cpu.writeToMem(0x200, 0x60);
        cpu.writeToMem(0x201, 0x51);

        //Set V1 to 51
        cpu.writeToMem(0x202, 0x61);
        cpu.writeToMem(0x203, 0x52);

        //Writes 0x8010, which sets VX to VY
        cpu.writeToMem(0x204, 0x80);
        cpu.writeToMem(0x205, 0x11);

        cpu.emulateCycle();
        assertEquals(0x51, cpu.getV(0));
        assertEquals(0x202, cpu.getPC());

        cpu.emulateCycle();
        assertEquals(0x52, cpu.getV(1));
        assertEquals(0x204, cpu.getPC());

        cpu.emulateCycle();
        assertEquals(0x206, cpu.getPC());
        assertEquals(0x52 | 0x51, cpu.getV(0));
        assertEquals(0x52, cpu.getV(1));
    }

    //Tests instruction 0x8XY2, Sets VX to VX & VY
    @Test
    public void regAndTest()
    {
        Chip8Cpu cpu = new Chip8Cpu();

        //Writes 0x6051, which sets V0 to 51
        cpu.writeToMem(0x200, 0x60);
        cpu.writeToMem(0x201, 0x51);

        //Set V1 to 51
        cpu.writeToMem(0x202, 0x61);
        cpu.writeToMem(0x203, 0x52);

        //Writes 0x8010, which sets VX to VY
        cpu.writeToMem(0x204, 0x80);
        cpu.writeToMem(0x205, 0x12);

        cpu.emulateCycle();
        assertEquals(0x51, cpu.getV(0));
        assertEquals(0x202, cpu.getPC());

        cpu.emulateCycle();
        assertEquals(0x52, cpu.getV(1));
        assertEquals(0x204, cpu.getPC());

        cpu.emulateCycle();
        assertEquals(0x206, cpu.getPC());
        assertEquals(0x52 & 0x51, cpu.getV(0));
        assertEquals(0x52, cpu.getV(1));
    }

    //Tests instruction 0x8XY3, Sets VX to VX ^ VY
    @Test
    public void regXorTest()
    {
        Chip8Cpu cpu = new Chip8Cpu();

        //Writes 0x6051, which sets V0 to 51
        cpu.writeToMem(0x200, 0x60);
        cpu.writeToMem(0x201, 0x51);

        //Set V1 to 51
        cpu.writeToMem(0x202, 0x61);
        cpu.writeToMem(0x203, 0x52);

        //Writes 0x8010, which sets VX to VY
        cpu.writeToMem(0x204, 0x80);
        cpu.writeToMem(0x205, 0x13);

        cpu.emulateCycle();
        assertEquals(0x51, cpu.getV(0));
        assertEquals(0x202, cpu.getPC());

        cpu.emulateCycle();
        assertEquals(0x52, cpu.getV(1));
        assertEquals(0x204, cpu.getPC());

        cpu.emulateCycle();
        assertEquals(0x206, cpu.getPC());
        assertEquals(0x52 ^ 0x51, cpu.getV(0));
        assertEquals(0x52, cpu.getV(1));
    }

    //Tests instruction 0x8XY4, Sets VX to VX + VY
    @Test
    public void regAddTest()
    {
        Chip8Cpu cpu = new Chip8Cpu();

        //Writes 0x6051, which sets V0 to 51
        cpu.writeToMem(0x200, 0x60);
        cpu.writeToMem(0x201, 0x51);

        //Set V1 to 51
        cpu.writeToMem(0x202, 0x61);
        cpu.writeToMem(0x203, 0x52);

        //Writes 0x8010, which sets VX to VY
        cpu.writeToMem(0x204, 0x80);
        cpu.writeToMem(0x205, 0x14);

        cpu.emulateCycle();
        assertEquals(0x51, cpu.getV(0));
        assertEquals(0x202, cpu.getPC());

        cpu.emulateCycle();
        assertEquals(0x52, cpu.getV(1));
        assertEquals(0x204, cpu.getPC());

        cpu.emulateCycle();
        assertEquals(0x206, cpu.getPC());
        assertEquals(0x52 + 0x51, cpu.getV(0));
        assertEquals(0x52, cpu.getV(1));
    }

    //Tests instruction 0x8XY4, Sets VX to VX + VY
    @Test
    public void regAddCarryTest()
    {
        Chip8Cpu cpu = new Chip8Cpu();

        //Writes 0x6051, which sets V0 to 51
        cpu.writeToMem(0x200, 0x60);
        cpu.writeToMem(0x201, 0xFF);

        //Set V1 to 51
        cpu.writeToMem(0x202, 0x61);
        cpu.writeToMem(0x203, 0x01);

        //Writes 0x8010, which sets VX to VY
        cpu.writeToMem(0x204, 0x80);
        cpu.writeToMem(0x205, 0x14);

        cpu.emulateCycle();
        assertEquals(0xFF, cpu.getV(0));
        assertEquals(0x202, cpu.getPC());

        cpu.emulateCycle();
        assertEquals(0x1, cpu.getV(1));
        assertEquals(0x204, cpu.getPC());

        cpu.emulateCycle();
        assertEquals(0x206, cpu.getPC());
        assertEquals(0x1, cpu.getV(0));
        assertEquals(0x1, cpu.getV(0xF));
        assertEquals(0x1, cpu.getV(1));
    }

    //Tests instruction 0x8XY5, Sets VX to VX - VY
    @Test
    public void regSubtractTest()
    {
        Chip8Cpu cpu = new Chip8Cpu();

        //Writes 0x6051, which sets V0 to 51
        cpu.writeToMem(0x200, 0x60);
        cpu.writeToMem(0x201, 0x52);

        //Set V1 to 51
        cpu.writeToMem(0x202, 0x61);
        cpu.writeToMem(0x203, 0x51);

        //Writes 0x8010, which sets VX to VY
        cpu.writeToMem(0x204, 0x80);
        cpu.writeToMem(0x205, 0x15);

        cpu.emulateCycle();
        assertEquals(0x52, cpu.getV(0));
        assertEquals(0x202, cpu.getPC());

        cpu.emulateCycle();
        assertEquals(0x51, cpu.getV(1));
        assertEquals(0x204, cpu.getPC());

        cpu.emulateCycle();
        assertEquals(0x206, cpu.getPC());
        assertEquals(0x52 - 0x51, cpu.getV(0));
        assertEquals(0x51, cpu.getV(1));
    }

    //Tests instruction 0x8XY4, Sets VX to VX + VY
    @Test
    public void regRightShiftTest()
    {
        Chip8Cpu cpu = new Chip8Cpu();

        //Writes 0x6051, which sets V0 to 51
        cpu.writeToMem(0x200, 0x60);
        cpu.writeToMem(0x201, 0x51);

        //Set V1 to 51
        cpu.writeToMem(0x202, 0x61);
        cpu.writeToMem(0x203, 0x52);

        //Writes 0x8010, which sets VX to VY
        cpu.writeToMem(0x204, 0x80);
        cpu.writeToMem(0x205, 0x16);

        cpu.emulateCycle();
        assertEquals(0x51, cpu.getV(0));
        assertEquals(0x202, cpu.getPC());

        cpu.emulateCycle();
        assertEquals(0x52, cpu.getV(1));
        assertEquals(0x204, cpu.getPC());

        cpu.emulateCycle();
        assertEquals(0x206, cpu.getPC());
        assertEquals(0x52 >> 1, cpu.getV(0));
        assertEquals(0x52, cpu.getV(1));
    }

    //Tests instruction 0x8XY7, Sets VX to VY - VX
    @Test
    public void regRevSubBorrowTest()
    {
        Chip8Cpu cpu = new Chip8Cpu();

        //Writes 0x6051, which sets V0 to 51
        cpu.writeToMem(0x200, 0x60);
        cpu.writeToMem(0x201, 0x51);

        //Set V1 to 51
        cpu.writeToMem(0x202, 0x61);
        cpu.writeToMem(0x203, 0x52);

        //Writes 0x8010, which sets VX to VY
        cpu.writeToMem(0x204, 0x80);
        cpu.writeToMem(0x205, 0x17);

        cpu.emulateCycle();
        assertEquals(0x51, cpu.getV(0));
        assertEquals(0x202, cpu.getPC());

        cpu.emulateCycle();
        assertEquals(0x52, cpu.getV(1));
        assertEquals(0x204, cpu.getPC());

        cpu.emulateCycle();
        assertEquals(0x206, cpu.getPC());
        assertEquals(0x51 - 0x52, cpu.getV(0));
        assertEquals(0x52, cpu.getV(1));
        assertEquals(0, cpu.getV(0xF));
    }

    //Tests instruction 0x8XYE, Sets VX to VX + VY
    @Test
    public void regLeftShiftTest()
    {
        Chip8Cpu cpu = new Chip8Cpu();

        //Writes 0x6051, which sets V0 to 51
        cpu.writeToMem(0x200, 0x60);
        cpu.writeToMem(0x201, 0x51);

        //Set V1 to 51
        cpu.writeToMem(0x202, 0x61);
        cpu.writeToMem(0x203, 0x82);

        //Writes 0x8010, which sets VX to VY
        cpu.writeToMem(0x204, 0x80);
        cpu.writeToMem(0x205, 0x1E);

        cpu.emulateCycle();
        assertEquals(0x51, cpu.getV(0));
        assertEquals(0x202, cpu.getPC());

        cpu.emulateCycle();
        assertEquals(0x82, cpu.getV(1));
        assertEquals(0x204, cpu.getPC());

        cpu.emulateCycle();
        assertEquals(0x206, cpu.getPC());
        assertEquals(0x82 << 1, cpu.getV(0));
        assertEquals(0x82 << 1, cpu.getV(1));
        assertEquals(0x82 >> 7, cpu.getV(0xF));
    }

    //Tests instruction 0xANNN, setting I to address NNN
    @Test
    public void setITest()
    {
        Chip8Cpu cpu = new Chip8Cpu();

        //Sets I to 3 at mem address 0x200
        cpu.writeToMem(0x200, 0xA1);
        cpu.writeToMem(0x201, 0x23);

        cpu.emulateCycle();

        assertTrue(cpu.getI() == 0x123);

    }

    //Tests the draw instruction, 0xDXYN
    @Test
    public void drawSpriteTest()
    {
        Chip8Cpu cpu = new Chip8Cpu();

        //Place sprite at memory location 0
        cpu.writeToMem(0, 0x3C);
        cpu.writeToMem(1, 0xC3);
        cpu.writeToMem(2, 0xFF);

        //Sets I at mem address 0x200 (program start)
        cpu.writeToMem(0x200, 0xA0);
        cpu.writeToMem(0x201, 0x00);


        //Place draw instruction at mem address 0x201
        cpu.writeToMem(0x202, 0xD0);
        cpu.writeToMem(0x203, 0x03);

        cpu.emulateCycle();
        assertTrue(cpu.getI() == 0);

        cpu.emulateCycle();


        String out = cpu.printGraphics();

        assertTrue(out.equals("0011110000000000000000000000000000000000000000000000000000000000\n" +
                "1100001100000000000000000000000000000000000000000000000000000000\n" +
                "1111111100000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n" +
                "0000000000000000000000000000000000000000000000000000000000000000\n"));

    }

}
