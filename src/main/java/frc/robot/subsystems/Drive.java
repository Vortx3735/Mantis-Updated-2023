// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;


import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import frc.robot.RobotContainer;

public class Drive extends SubsystemBase {
  // create drivebase motors
  public static TalonFX right_front_motor = new TalonFX(10);
  public static TalonFX right_back_motor = new TalonFX(1); 
  public static TalonFX left_front_motor = new TalonFX(2);    
  public static TalonFX left_back_motor = new TalonFX(3); 

  private double move = 0;
  private double rotate = 0;

  public Drive(double move, double rotate) {
    config();
    
  }

  public void config()
  {
    // do you need that????????????????????????????????/
    // why do you need to set it to NOT inverted if that's how it already is
    right_front_motor.setInverted(false);   
    right_back_motor.follow(right_front_motor);
    left_front_motor.setInverted(true);   
    left_back_motor.setInverted(true);
    left_back_motor.follow(left_front_motor);
  }

  public void test()
  {
    right_front_motor.set(ControlMode.PercentOutput,.3);
  }

  public void tankDrive(double left, double right)
  {
      right_front_motor.set(ControlMode.PercentOutput, -right);
      left_front_motor.set(ControlMode.PercentOutput, -left);
  }

  public void autoConfig()
  {
    right_back_motor.follow(right_front_motor);
    left_back_motor.follow(left_front_motor);
    right_front_motor.setInverted(false);
    left_front_motor.setInverted(false);
  }

  public void setDrive(double move, double rotation)
  {
    this.move = move;
    this.rotate = rotation;
  }

  public void arcadeDrive(double move, double rotation)
  {
    // This is defining the speed of both sides of the drive train based off of direction and rotation values (right is positive, left is negative)
    right_front_motor.set(ControlMode.PercentOutput, move - rotation);
    left_front_motor.set(ControlMode.PercentOutput, move + rotation);
   
  }

  @Override
  public void periodic() {


    if(RobotContainer.main.r2.getAsBoolean())
    {
    arcadeDrive(.5 + (-RobotContainer.main.getLeftY()/2), RobotContainer.main.getLeftX()/1.15);
    } else  if(RobotContainer.main.l2.getAsBoolean()){
      arcadeDrive(-.5 + (RobotContainer.main.getLeftY()/2), RobotContainer.main.getLeftX()/1.15);
    }else if(!Robot.auton && !Robot.limelight){
      tankDrive(RobotContainer.main.getLeftY(), RobotContainer.main.getRightY());
    }

    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
