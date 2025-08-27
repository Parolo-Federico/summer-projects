class Robot {
    private int width;
    private int height;
    private String dir;
    //private int[] pos; // pos[0] = x Table; pos[1] = y Table
    private int x;
    private int y;

    /*
    ^
    |
    Y  y
    |                                            N
                                              W  +  E
    height = 6; width = 6                        S
    5 |   |   |   |   |   |   |
    4 |   |   |   |   |   |   |
    3 |   |   |   |   |   |   |
    2 |   |   |   |   |   |   |
    1 |   |   |   |   |   |   |
    0 | R |   |   |   |   |   |
        0   1   2   3   4   5  --X-->  x


    */
    public Robot(int width, int height) {
        this.width = width;
        this.height = height;
        dir = "East";
        x = 0;
        y = 0;
    }

    public void step(int num) {
        for (int i = 0; i < num; i++) {
            boolean stepDone = false;
            while(!stepDone){
                switch (dir){
                    case "North":
                        if (y+1 < height){
                            y+=1;
                            stepDone = !stepDone;
                        }else{
                            dir = "West";
                        }
                        break;

                    case "South":
                        if (y-1 >= 0){
                            y-=1;
                            stepDone = !stepDone;
                        }else{
                            dir = "East";
                        }
                        break;

                    case "East":
                        if (x+1 < width){
                            x+=1;
                            stepDone = !stepDone;
                        }else {
                            dir = "North";
                        }
                        break;

                    case "West":
                        if (x-1 >= 0){
                            x-=1;
                            stepDone = !stepDone;
                        }else{
                            dir = "South";
                        }
                        break;
                }
            }

        }
    }

    public int[] getPos() {
        return new int[]{x,y};
    }

    public String getDir() {
        return dir;
    }
}

/**
 * Your Robot object will be instantiated and called as such:
 * Robot obj = new Robot(width, height);
 * obj.step(num);
 * int[] param_2 = obj.getPos();
 * String param_3 = obj.getDir();
 */