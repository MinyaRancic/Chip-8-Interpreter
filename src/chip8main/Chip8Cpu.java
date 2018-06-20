package chip8main;

import java.util.Random;

public class Chip8Cpu {
    private int memory[];
    //Registers
    private int V[];
    //Index Register
    private int I;
    //Program Counter
    private int pc;
    //Represents the internal state of the graphics
    private int gfx[];

    private int delayTimer;
    private int soundTimer;

    private int key[];

    private int opcode;

    private int sp;
    private int stack[];

    private boolean drawFlag;

    private Random rand;

    private char chip8_fontset[] =
    {
        0xF0, 0x90, 0x90, 0x90, 0xF0, // 0
                0x20, 0x60, 0x20, 0x20, 0x70, // 1
                0xF0, 0x10, 0xF0, 0x80, 0xF0, // 2
                0xF0, 0x10, 0xF0, 0x10, 0xF0, // 3
                0x90, 0x90, 0xF0, 0x10, 0x10, // 4
                0xF0, 0x80, 0xF0, 0x10, 0xF0, // 5
                0xF0, 0x80, 0xF0, 0x90, 0xF0, // 6
                0xF0, 0x10, 0x20, 0x40, 0x40, // 7
                0xF0, 0x90, 0xF0, 0x90, 0xF0, // 8
                0xF0, 0x90, 0xF0, 0x10, 0xF0, // 9
                0xF0, 0x90, 0xF0, 0x90, 0x90, // A
                0xE0, 0x90, 0xE0, 0x90, 0xE0, // B
                0xF0, 0x80, 0x80, 0x80, 0xF0, // C
                0xE0, 0x90, 0x90, 0x90, 0xE0, // D
                0xF0, 0x80, 0xF0, 0x80, 0xF0, // E
                0xF0, 0x80, 0xF0, 0x80, 0x80  // F
    };

    public Chip8Cpu()
    {
        V = new int[16];
        key = new int[16];
        memory = new int[4096];
        gfx = new int[64*32];
        stack = new int[16];


        // Chip-8 CPU Constants
        pc = 0x200;
        opcode = 0;
        I = 0;
        sp = 0;

        for(int i = 0; i < 80; i++)
        {
            memory[i] = chip8_fontset[i];
        }

        //Tools
        rand = new Random();
    }

    public void emulateCycle()
    {
        //Fetch Opcode
        opcode = memory[pc] << 8 | memory[pc + 1];
        switch((opcode & 0xF000) >> 12)
        {
            case 0:
                if((opcode & 0x0F00) == 0)
                {
                    // Opcode 0x00E0, clears the screen
                    if((opcode & 0x00FF) == 0xE0)
                    {
                        for(int i = 0; i < 64*32; i++)
                        {
                            gfx[i] = 0;
                        }
                        pc += 2;
                    }
                    else // Opcode 0x00EE, return from subroutine
                    {
                        pc = stack[--sp];
                    }
                }
                break;

            case 1: //Opcode 0x1NNN, jmp to address NNN
                pc = opcode & 0x0FFF;
                break;

            case 2: //Opcode 0x2NNN, call routine at address NNN
                stack[sp++] = pc + 2;
                pc = opcode & 0x0FFF;
                break;

            case 3: //Opcode 0x3XNN, skip next instr if VX = NN
                if(V[(opcode & 0x0F00) >> 8] == (opcode & 0x00FF))
                {
                    pc += 2;
                }
                pc += 2;
                break;

            case 4: //Opcode 0x4XNN, skip next instr if VX != NN
                if(V[(opcode & 0x0F00) >> 8] != (opcode & 0x00FF))
                {
                    pc += 2;
                }
                pc += 2;
                break;

            case 5: //Opcode 0x5XY0, skip next instr if VX = VY
                if(V[(opcode & 0x0F00) >> 8] == V[(opcode & 0x00F0) >> 4])
                {
                    pc += 2;
                }
                pc += 2;
                break;

            case 6: //Opcode 0x6XNN, set VX to NN
                V[(opcode & 0x0F00) >> 8] = (opcode & 0x00FF);
                pc += 2;
                break;

            case 7: //Opcode 0x7XNN, add NN to VX
                V[(opcode & 0x0F00) >> 8] += (opcode & 0x00FF);
                pc += 2;
                break;

            case 8:
            {
                switch(opcode & 0x000F)
                {
                    case 0: //Opcode 0x8XY0, sets VX to VY
                        V[(opcode & 0x0F00) >> 8] = V[(opcode & 0x00F0) >> 4];
                        break;

                    case 1: //Opcode 0x8XY1, sets VX to VX | VY
                        V[(opcode & 0x0F00) >> 8] |= V[(opcode & 0x00F0) >> 4];
                        break;

                    case 2: //Opcode 0x8XY2, sets VX to VX & VY
                        V[(opcode & 0x0F00) >> 8] &= V[(opcode & 0x00F0) >> 4];
                        break;

                    case 3: //Opcode 0x8XY3, sets VX to VX ^ VY
                        V[(opcode & 0x0F00) >> 8] ^= V[(opcode & 0x00F0) >> 4];
                        break;

                    case 4: //Opcode 0x8XY4, sets VX to VX + VY
                        V[(opcode & 0x0F00) >> 8] += V[(opcode & 0x00F0) >> 4];

                        //Carry Bit Implementation
                        if(V[(opcode & 0x0F00) >> 8] > 255)
                        {
                            V[(opcode & 0x0F00) >> 8] -= 255;
                            V[0xF] = 1;
                        } else
                        {
                            V[0xF] = 0;
                        }

                        break;
//TODO Fix Register Meta Info
                    case 5: //Opcode 0x8XY5, sets VX to VX - VY
                        V[(opcode & 0x0F00) >> 8] -= V[(opcode & 0x00F0) >> 4];
                        break;

                    case 6: //Opcode 0x8XY6, sets VX to VY >> 1
                        V[(opcode & 0x0F00) >> 8] = (V[(opcode & 0x00F0) >> 4] >> 1);
                        break;

                    case 7: //Opcode 0x8XY1, sets VX to VY - VX
                        V[(opcode & 0x0F00) >> 8] = (V[(opcode & 0x00F0) >> 4] - V[(opcode & 0x0F00) >> 8]);
                        break;

                    case 0xE: //Opcode 0x8XY1, shifts VY left by 1 and sets VX equal to it
                        V[(opcode & 0x0F00) >> 8] = (V[(opcode & 0x00F0) >> 4] <<= 1);
                        break;

                }
                pc += 2;
                break;
            }
            case 9: //Opcode 0x9XY0, skips next instr if VX != VY
                if(V[(opcode & 0x0F00) >> 8] != V[(opcode & 0x00F0) >> 4])
                {
                    pc += 2;
                }
                pc += 2;
                break;

            case 0xA: //Opcode 0xANNN, sets I to address NNN
                I = (opcode & 0x0FFF);
                pc += 2;
                break;

            case 0xB: //Opcode 0xBNNN, jumps to address V0+NNN
                pc = V[0] + (opcode & 0x0FFF);
                break;

            case 0xC: //Opcode 0xCXNN, sets VX to the result of NN & rand
                V[opcode & 0x0F00] = rand.nextInt(256) & (opcode & 0x00FF);
                pc += 2;

            case 0xD: //Opcode 0xDXYN, draws a sprite at VX, VY, with a height of N and a width of 8 pixels.
                //Sprites are read from the address of I
                int height = opcode & 0x000F;
                int x = V[(opcode & 0x0F00) >> 8], y = V[(opcode & 0x00F0) >> 4];
                V[0xF] = 0;

                for (int yline = 0; yline < height; yline++)
                {
                    int pixel = memory[I + yline]; // Grabs byte that contains line of the sprite
                    for(int xline = 0; xline < 8; xline++)
                    {
                        if((pixel & (0x80 >> xline)) != 0) // Grabs
                        {
                            if(gfx[(x + xline + ((y + yline) * 64))] == 1)
                                V[0xF] = 1;
                            gfx[x + xline + ((y + yline) * 64)] ^= 1;
                        }
                    }
                }

                drawFlag = true;
                pc += 2;
                break;

            case 0xE:
            {
                if((opcode & 0x00FF) == 0x9E) //Opcode 0xEX9E, skip next instr if the key in VX is pressed
                {
                    if(key[V[(opcode & 0x0F00) >> 8]] == 1)
                    {
                        pc += 2;
                    }
                    pc += 2;
                } else //opcode 0xEXA1, skip next instr if the key in VX is NOT pressed
                {
                    if(key[V[(opcode & 0x0F00) >> 8]] == 0)
                    {
                        pc += 2;
                    }
                    pc += 2;
                }
                break;
            }

            case 0xF:
            {
                switch(opcode & 0x00FF)
                {
                    case 0x07: //Opcode 0xFX07, sets VX to the value of the delay timer
                        V[(opcode & 0x0F00) >> 8] = delayTimer;
                        pc += 2;
                        break;

                    case 0x0A: //Opcode 0xFX0A, Waits for a key press and stores it in VX (Blocking Op)
                        boolean keyPressed = false;
                        for(int i = 0; i < 16; i++)
                        {
                            if(key[i] != 0)
                            {
                                V[(opcode & 0x0F00) >> 8] = i;
                                keyPressed = true;
                            }
                        }

                        if(!keyPressed)
                        {
                            return;
                        }

                        pc += 2;
                        break;

                    case 0x15: //Opcode 0xFX15, sets the delay timer to VX
                        delayTimer = V[(opcode & 0x0F00) >> 8];
                        pc += 2;
                        break;

                    case 0x18: //Opcode 0xFX18, sets the sound timer to VX
                        soundTimer = V[(opcode & 0x0F00) >> 8];
                        pc += 2;
                        break;

                    case 0x1E: //Opcode 0xFX1E, adds VX to I
                        I += V[(opcode & 0x0F00) >> 8];
                        pc += 2;
                        break;

                    case 0x29: //Opcode 0xFX29, sets I to the location of character in VX
                        I = V[(opcode & 0x0F00) >> 8] * 5;
                        pc += 2;
                        break;

                    case 0x33: //Opcode 0xFX33,
                        memory[I]     = V[(opcode & 0x0F00) >> 8] / 100;
                        memory[I + 1] = (V[(opcode & 0x0F00) >> 8] / 10) % 10;
                        memory[I + 2] = (V[(opcode & 0x0F00) >> 8] % 100) % 10;
                        pc += 2;
                        break;

                    case 0x55: //Opcode 0xFS55, stores V0 through VX registers in memory starting at address I
                    {
                        int last = (opcode & 0x0F00) >> 8;
                        for (int i = 0; i < last; i++) {
                            memory[I + i] = V[i];
                        }
                        pc += 2;
                        break;
                    }

                    case 0x65: //Opcode 0xFX65, fills V0 to VX from memory starting at address I
                        int last = (opcode & 0x0F00) >> 8;
                        for(int i = 0; i < last; i++)
                        {
                            V[i] = memory[I + i];
                        }
                        pc += 2;
                        break;

                }
            }


        }

        if(delayTimer > 0) delayTimer--;

        if(soundTimer > 0)
        {
            if(soundTimer == 1) System.out.println("BEEP!");
            soundTimer--;
        }
    }

    public void writeToMem(int location, int val)
    {
        memory[location] = val;
    }

    public String dumpMemory()
    {
        String result = "";

        for(int i = 0; i < 64; i++)
        {
            for(int j = 0; j < 64; j++)
            {
                result += (memory[i*64 + j] + " ");
            }
            result += "\n";
        }

        return result;
    }

    public String printGraphics()
    {
        String result = "";

        for(int i = 0; i < 32; i++)
        {
            for(int j = 0; j < 64; j++)
            {
                result += gfx[i*64 + j];
            }
            result += "\n";
        }

        return result;
    }

    public boolean getDrawFlag()
    {
        return drawFlag;
    }

    public int getI()
    {
        return I;
    }

    public int getPC()
    {
        return pc;
    }

    public int getV(int i) {
        return V[i];
    }
}
