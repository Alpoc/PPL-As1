//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class VPL {
    static final int max = 10000;
    static int[] mem = new int[10000];
    static int ip;
    static int bp;
    static int sp;
    static int rv;
    static int hp;
    static int numPassed;
    static int gp;
    static String fileName;
    static int texShots = 0;
    private static final int noopCode = 0;
    private static final int labelCode = 1;
    private static final int callCode = 2;
    private static final int passCode = 3;
    private static final int allocCode = 4;
    private static final int returnCode = 5;
    private static final int getRetvalCode = 6;
    private static final int jumpCode = 7;
    private static final int condJumpCode = 8;
    private static final int addCode = 9;
    private static final int subCode = 10;
    private static final int multCode = 11;
    private static final int divCode = 12;
    private static final int remCode = 13;
    private static final int equalCode = 14;
    private static final int notEqualCode = 15;
    private static final int lessCode = 16;
    private static final int lessEqualCode = 17;
    private static final int andCode = 18;
    private static final int orCode = 19;
    private static final int notCode = 20;
    private static final int oppCode = 21;
    private static final int litCode = 22;
    private static final int copyCode = 23;
    private static final int getCode = 24;
    private static final int putCode = 25;
    private static final int haltCode = 26;
    private static final int inputCode = 27;
    private static final int outputCode = 28;
    private static final int newlineCode = 29;
    private static final int symbolCode = 30;
    private static final int newCode = 31;
    private static final int allocGlobalCode = 32;
    private static final int toGlobalCode = 33;
    private static final int fromGlobalCode = 34;
    private static final int debugCode = 35;

    public VPL() {
    }

    public static void main(String[] var0) throws Exception {
        BufferedReader var1 = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("enter name of file containing VPL program: ");
        fileName = var1.readLine();
        BufferedReader var2 = new BufferedReader(new FileReader(fileName));
        ArrayList var6 = new ArrayList();
        ArrayList var7 = new ArrayList();
        int var9 = 0;

        String var3;
        int var8;
        int var10;
        do {
            var3 = var2.readLine();
            System.out.println("parsing line [" + var3 + "]");
            if(var3 != null) {
                StringTokenizer var4 = new StringTokenizer(var3);
                if(var4.countTokens() > 0) {
                    int var5 = Integer.parseInt(var4.nextToken());
                    if(var5 == 1) {
                        var8 = Integer.parseInt(var4.nextToken());
                        var6.add(new IntPair(var8, var9));
                    } else {
                        mem[var9] = var5;
                        ++var9;
                        if(var5 == 2 || var5 == 7 || var5 == 8) {
                            var8 = Integer.parseInt(var4.nextToken());
                            mem[var9] = var8;
                            var7.add(new IntPair(var9, var8));
                            ++var9;
                        }

                        for(var10 = 0; var10 < numArgs(var5); ++var10) {
                            mem[var9] = Integer.parseInt(var4.nextToken());
                            ++var9;
                        }
                    }
                }
            }
        } while(var3 != null);

        int var11;
        for(var11 = 0; var11 < var7.size(); ++var11) {
            var8 = ((IntPair)var7.get(var11)).second;
            var10 = -1;

            for(int var12 = 0; var12 < var6.size(); ++var12) {
                if(((IntPair)var6.get(var12)).first == var8) {
                    var10 = ((IntPair)var6.get(var12)).second;
                }
            }

            mem[((IntPair)var7.get(var11)).first] = var10;
        }

        System.out.println("after replacing labels:");
        showMem(0, var9 - 1);
        bp = var9;
        sp = var9 + 2;
        ip = 0;
        rv = -1;
        hp = 10000;
        numPassed = 0;
        var11 = bp - 1;
        System.out.println("Code is ");
        showMem(0, var11);
        gp = var11 + 1;
        boolean var18 = false;
        boolean var14 = false;
        boolean var15 = false;
        boolean var16 = false;

        do {
            int var13 = mem[ip];
            ++ip;
            int var19 = -1;
            int var20 = -2;
            int var21 = -3;
            int var17;
            if(var13 != 2 && var13 != 7 && var13 != 8) {
                var17 = numArgs(var13);
            } else {
                var17 = numArgs(var13) + 1;
            }

            if(var17 == 1) {
                var19 = mem[ip];
                ++ip;
            } else if(var17 == 2) {
                var19 = mem[ip];
                ++ip;
                var20 = mem[ip];
                ++ip;
            } else if(var17 == 3) {
                var19 = mem[ip];
                ++ip;
                var20 = mem[ip];
                ++ip;
                var21 = mem[ip];
                ++ip;
            }

            if(var13 != 0) {
                if(var13 == 2) {
                    mem[sp] = bp;
                    mem[sp + 1] = ip;
                    bp = sp;
                    sp += 2 + numPassed;
                    ip = var19;
                    numPassed = 0;
                } else if(var13 == 3) {
                    mem[sp + numPassed + 2] = mem[bp + 2 + var19];
                    ++numPassed;
                } else if(var13 == 4) {
                    sp += var19;
                } else if(var13 == 5) {
                    rv = mem[bp + 2 + var19];
                    ip = mem[bp + 1];
                    sp = bp;
                    bp = mem[bp];
                } else if(var13 == 6) {
                    mem[bp + 2 + var19] = rv;
                } else if(var13 == 7) {
                    ip = var19;
                } else if(var13 == 8) {
                    if(mem[bp + 2 + var20] != 0) {
                        ip = var19;
                    }
                } else if(var13 == 9) {
                    mem[bp + 2 + var19] = mem[bp + 2 + var20] + mem[bp + 2 + var21];
                } else if(var13 == 10) {
                    mem[bp + 2 + var19] = mem[bp + 2 + var20] - mem[bp + 2 + var21];
                } else if(var13 == 11) {
                    mem[bp + 2 + var19] = mem[bp + 2 + var20] * mem[bp + 2 + var21];
                } else if(var13 == 12) {
                    mem[bp + 2 + var19] = mem[bp + 2 + var20] / mem[bp + 2 + var21];
                } else if(var13 == 13) {
                    mem[bp + 2 + var19] = mem[bp + 2 + var20] % mem[bp + 2 + var21];
                } else if(var13 == 14) {
                    if(mem[bp + 2 + var20] == mem[bp + 2 + var21]) {
                        mem[bp + 2 + var19] = 1;
                    } else {
                        mem[bp + 2 + var19] = 0;
                    }
                } else if(var13 == 15) {
                    if(mem[bp + 2 + var20] != mem[bp + 2 + var21]) {
                        mem[bp + 2 + var19] = 1;
                    } else {
                        mem[bp + 2 + var19] = 0;
                    }
                } else if(var13 == 16) {
                    if(mem[bp + 2 + var20] < mem[bp + 2 + var21]) {
                        mem[bp + 2 + var19] = 1;
                    } else {
                        mem[bp + 2 + var19] = 0;
                    }
                } else if(var13 == 17) {
                    if(mem[bp + 2 + var20] <= mem[bp + 2 + var21]) {
                        mem[bp + 2 + var19] = 1;
                    } else {
                        mem[bp + 2 + var19] = 0;
                    }
                } else if(var13 == 18) {
                    if(mem[bp + 2 + var20] != 0 && mem[bp + 2 + var21] != 0) {
                        mem[bp + 2 + var19] = 1;
                    } else {
                        mem[bp + 2 + var19] = 0;
                    }
                } else if(var13 == 19) {
                    if(mem[bp + 2 + var20] == 0 && mem[bp + 2 + var21] == 0) {
                        mem[bp + 2 + var19] = 0;
                    } else {
                        mem[bp + 2 + var19] = 1;
                    }
                } else if(var13 == 20) {
                    if(mem[bp + 2 + var20] != 0) {
                        mem[bp + 2 + var19] = 0;
                    } else {
                        mem[bp + 2 + var19] = 1;
                    }
                } else if(var13 == 21) {
                    mem[bp + 2 + var19] = -mem[bp + 2 + var20];
                } else if(var13 == 22) {
                    mem[bp + 2 + var19] = var20;
                } else if(var13 == 23) {
                    mem[bp + 2 + var19] = mem[bp + 2 + var20];
                } else if(var13 == 24) {
                    mem[bp + 2 + var19] = mem[mem[bp + 2 + var20] + mem[bp + 2 + var21]];
                } else if(var13 == 25) {
                    mem[mem[bp + 2 + var19] + mem[bp + 2 + var20]] = mem[bp + 2 + var21];
                } else if(var13 == 26) {
                    System.out.println("\nHalting.......");
                    System.exit(0);
                } else if(var13 == 27) {
                    System.out.print("? ");
                    mem[bp + 2 + var19] = Integer.parseInt(var1.readLine());
                } else if(var13 == 28) {
                    System.out.print(mem[bp + 2 + var19]);
                } else if(var13 == 29) {
                    System.out.println("");
                } else if(var13 == 30) {
                    System.out.print((char)mem[bp + 2 + var19]);
                } else if(var13 == 31) {
                    hp -= mem[bp + 2 + var20];
                    mem[bp + 2 + var19] = hp;
                } else if(var13 == 32) {
                    bp = gp + var19;
                    sp = bp + 2;
                } else if(var13 == 33) {
                    mem[gp + var19] = mem[bp + 2 + var20];
                } else if(var13 == 34) {
                    mem[bp + 2 + var19] = mem[gp + var20];
                } else if(var13 != 35) {
                    System.out.println("Fatal error: unknown opcode [" + var13 + "]");
                    System.exit(1);
                }
            }
        } while(!var18);

    }

    private static int numArgs(int var0) {
        if(var0 == 1) {
            return 1;
        } else if(var0 == 7) {
            return 0;
        } else if(var0 == 8) {
            return 1;
        } else if(var0 == 2) {
            return 0;
        } else if(var0 != 0 && var0 != 26 && var0 != 29 && var0 != 35) {
            if(var0 != 3 && var0 != 4 && var0 != 5 && var0 != 6 && var0 != 27 && var0 != 28 && var0 != 30 && var0 != 32) {
                if(var0 != 20 && var0 != 21 && var0 != 22 && var0 != 23 && var0 != 31 && var0 != 33 && var0 != 34) {
                    if(var0 != 9 && var0 != 10 && var0 != 11 && var0 != 12 && var0 != 13 && var0 != 14 && var0 != 15 && var0 != 16 && var0 != 17 && var0 != 18 && var0 != 19 && var0 != 24 && var0 != 25) {
                        System.out.println("Fatal error: unknown opcode [" + var0 + "]");
                        System.exit(1);
                        return -1;
                    } else {
                        return 3;
                    }
                } else {
                    return 2;
                }
            } else {
                return 1;
            }
        } else {
            return 0;
        }
    }

    private static void showMem(int var0, int var1) {
        for(int var2 = var0; var2 <= var1; ++var2) {
            System.out.println(var2 + ": " + mem[var2]);
        }

    }

    private static void generatePiCTeX(int var0) throws Exception {
        PrintWriter var1 = new PrintWriter(new FileWriter(fileName + texShots + ".tex"));
        ++texShots;
        System.out.println("TeX output " + (texShots - 1) + " has sp = " + sp);
        var1.println("%" + fileName + (texShots - 1) + ".tex:");
        var1.println("\\input pictex");
        var1.println("\\medskip");
        var1.println("\\beginpicture");
        var1.println("\\setcoordinatesystem units <0.4true in, 0.4true in>");
        byte var2 = 10;
        double var3 = 1.0D;
        double var5 = 1.0D;
        double var7 = 1.25D;
        double var9 = 0.08D;
        double var11 = 0.4D;
        byte var13 = 10;
        int var15 = 0;
        double var16 = 0.0D;
        double var18 = (double)((sp + var0) / var2) * (var5 + var7);
        int var20 = 0;

        for(int var14 = 0; var14 < sp + var0; ++var14) {
            var1.println("\\putrectangle corners at " + var16 + " " + var18 + " and " + (var16 + var3) + " " + (var18 + var5));
            if(var14 < sp) {
                var1.println("\\put {$" + mem[var14] + "$} at " + (var16 + var3 / 2.0D) + " " + (var18 + var5 / 2.0D));
            }

            var1.println("\\put {$" + var14 + "$} [b] at " + (var16 + var3 / 2.0D) + " " + (var18 + var5 + var9));
            if(var14 == ip) {
                var1.println("\\put {\\tt ip} [b] at " + (var16 + var3 / 2.0D) + " " + (var18 + var5 + var11));
            }

            if(var14 == bp) {
                var1.println("\\put {\\tt bp} [b] at " + (var16 + var3 / 2.0D) + " " + (var18 + var5 + var11));
            }

            if(var14 == sp) {
                var1.println("\\put {\\tt sp} [b] at " + (var16 + var3 / 2.0D) + " " + (var18 + var5 + var11));
            }

            var16 += var3;
            ++var15;
            if(var15 == var2) {
                var16 = 0.0D;
                var18 -= var7 + var5;
                var15 = 0;
                ++var20;
                if(var20 == var13) {
                    var1.println("\\endpicture\n\n");
                    var1.println("\\vfil\\eject");
                    var1.println("\\beginpicture");
                    var1.println("\\setcoordinatesystem units <0.4true in, 0.4true in>");
                    var20 = 0;
                    var18 = (double)((sp + var0) / var2) * (var5 + var7);
                }
            }
        }

        var1.println("\\endpicture");
        var1.println("\\vfil\\eject\\bye");
        var1.close();
    }
}
