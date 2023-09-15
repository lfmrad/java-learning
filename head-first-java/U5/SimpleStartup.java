class SimpleStartup {
    private int[] locationCells;
    private int numOfHits = 0;
    private int lastCorrectGuess = -1; // to not interfere with guess = 0

    public void setLocationCells(int[] locs) {
        locationCells = locs; 
    }

    public String checkYourself(int guess) {
        String result = "miss";
        for (int cell : locationCells) {
            if (guess == cell && guess != lastCorrectGuess) {
                result = "hit";
                numOfHits++;
                // avoids being able to hit already hit cell
                lastCorrectGuess = guess;
                break;
            } else if (guess == lastCorrectGuess) {
                result = "already hit";
                break;
            }
        }
        if (numOfHits == locationCells.length) {
            result = "kill";
        }
        System.out.println(result);
        return result;
    }
    
}