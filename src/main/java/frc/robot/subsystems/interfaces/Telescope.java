package frc.robot.subsystems.interfaces;

public interface Telescope {
    public static class TelescopeSettings {
        public double SimpleMotorFeedforward;
        public double minLength;
        public double maxLength;
        public double SetpointPositionTolerance;

         // Sim stuff goes here 
    } 
    /**
     * Returns the current length of the telescope
     *
     * @return the current length of the telescope
     */
        public double getCurrentLength();
    
        /**
         * Sets the target length for the telescope
         *
         * @param length the target length
         */
        public void setTargetLength(double length);
    
        /**
         * Returns the target length for the telescope
         *
         * @return the target length
         */
        public double getTargetLength();
    
        /**
         * Checks if the telescope is at its target length
         *
         * @return true if the telescope is at its target length, false otherwise
         */
        public boolean isAtSetpoint();
        /**
         * Returns the current velocity of the telescope
         *
         * @return the current velocity of the telescope
         */
    
        public double getCurrentVelocity();
        
        /**
         * Returns the telescope settings
         *
         * @return the current telescope settings
         */
    
        public TelescopeSettings getSettings();
    
}
