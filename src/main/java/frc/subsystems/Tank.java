package frc.subsystems;
//Domain expansion infinite imports
//Comments courtesy of cool dude Des
import java.security.Key;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import redrocklib.logging.SmartDashboardNumber;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;

public class Tank extends SubsystemBase {
    private static Tank instance = null;
    //Make the motors
    private SparkMax frontLeftMotor = new SparkMax(0, MotorType.kBrushless);
    private SparkMax backLeftMotor = new SparkMax(0, MotorType.kBrushless);
    private SparkMax frontRightMotor = new SparkMax(0, MotorType.kBrushless);
    private SparkMax backRightMotor = new SparkMax(0, MotorType.kBrushless);

    private SmartDashboardNumber speedLimit = new SmartDashboardNumber("tank/speedLimit", 1);

    private Tank() {
       SparkMaxConfig leftConfig = new SparkMaxConfig();
       SparkMaxConfig rightConfig = new SparkMaxConfig();

       //Invert left side to account for the physical rotation of the motor
       leftConfig.idleMode(IdleMode.kBrake).inverted(true);
       rightConfig.idleMode(IdleMode.kBrake).inverted(false);
        
       //Do the silly config type shi
       frontLeftMotor.configure(leftConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters)
       backLeftMotor.configure(leftConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters)
       frontRightMotor.configure(rightConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters)
       backRightMotor.configure(rightConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters)
    }
    public void calcMotorVelocity(double verticalAxis, double horizontalAxis){
        setLeftMotorSpeed(Math.max(Math.min(verticalAxis + horizontalAxis, speedLimit.getNumber()),-speedLimit.getNumber()));
        setRightMotorSpeed(Math.max(Math.min(verticalAxis - horizontalAxis, speedLimit.getNumber()),-speedLimit.getNumber()));
        SmartDashboard.putNumber("tank/axes/vertical", verticalAxis);
        SmartDashboard.putNumber("tank/axes/horizontal", horizontalAxis);

    } 
    private void setRightMotorSpeed(double speed){
        frontRightMotor.set(speed);
        backRightMotor.set(speed);
        SmartDashboard.putNumber("tank/speedRight", speed);
    }
    private void setLeftMotorSpeed(double speed){
        frontLeftMotor.set(speed);
        backLeftMotor.set(speed);
        SmartDashboard.putNumber("tank/speedLeft", speed);
    }
    public static Tank getInstance() {
        if (instance == null) instance = new Tank();
        return instance;
    }
}
