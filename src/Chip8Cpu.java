public class Chip8Cpu {
    int memory[];
    //Registers
    int V[];
    //Index Register
    int I;
    //Program Counter
    int pc;
    //Represents the internal state of the graphics
    int gfx[];

    int delayTimer;
    int soundTimer;

    int key[];

    int opcode;

    int sp;
    int stack[];

    public Chip8Cpu()
    {
        V = new int[16];
        key = new int[16];
        memory = new int[4096];
        gfx = new int[16*32];
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
    }

    public void emulateCycle()
    {
        //Fetch Opcode
        opcode = memory[pc] << 8 | memory[pc + 1];
        switch(opcode & 0xF000)
        {
            case 0:
                if((opcode & 0x0F00) == 0)
                {
                    // Opcode 0x0E0
                    if((opcode & 0x00FF) == 0xE0)
                    {
                        for(int i = 0; i < 64*32; i++)
                        {
                            gfx[i] = 0;
                        }
                    }
                } else // Opcode 0x0EE, return from subroutine
                {
                    pc = stack[--sp];
                }
                break;

            case 1: //Opcode 0x1NNN, jmp to address NNN
                pc = opcode & 0x0FFF;
                break;

            case 2: //Opcode 0x2NNN, call routine at address NNN
                stack[sp++] = pc;
                pc = opcode & 0x0FFF;
                break;

            case 3: //Opcode 0x3XNN, skip next instr if VX = NN
                if(V[opcode & 0x0F00] == (opcode & 0x00FF))
                {
                    pc += 2;
                }
                break;

            case 4: //Opcode 0x4XNN, skip next instr if VX != NN
                if(V[opcode & 0x0F00] != (opcode & 0x00FF))
                {
                    pc += 2;
                }
                break;

            case 5: //Opcode 0x5XY0, skip next instr if VX = VY
                if(V[opcode & 0x0F00] == V[opcode & 0x00F0])
                {
                    pc += 2;
                }
                break;

            case 6: //Opcode 0x6XNN, set VX to NN
                V[opcode & 0x0F00] = (opcode & 0x00FF);
                break;

            case 7: //Opcode 0x7XNN, add NN to VX
                V[opcode & 0x0F00] += (opcode & 0x00FF);
                break;

            case 8:
            {
                switch(opcode & 0x000F)
                {
                    case 0: //Opcode 0x8XY0, sets VX to VY
                        V[opcode & 0x0F00] = V[opcode & 0x00F0];
                        break;

                    case 1: //Opcode 0x8XY1, sets VX to VX | VY
                        V[opcode & 0x0F00] |= V[opcode & 0x00F0];
                        break;

                }
            }

        }

        if(delayTimer > 0) delayTimer--;

        if(soundTimer > 0)
        {
            if(soundTimer == 1) System.out.println("BEEP!");
        }
    }

    public void writeToMem(int location, char val)
    {
        memoery[location] = val;
    }

    public void dumpMemory()
    {
        for(int i = 0; i < 64; i++)
        {
            for(int j = 0; j < 64; j++)
            {
                System.out.println(memory[i*64 + j] + " ");
            }
        }
    }
}
