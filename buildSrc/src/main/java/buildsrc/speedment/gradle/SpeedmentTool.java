package buildsrc.speedment.gradle;

import com.speedment.common.injector.Injector;
import com.speedment.runtime.core.Speedment;
import com.speedment.tool.core.MainApp;
import java.io.File;
import java.util.Optional;
import javafx.application.Application;

public class SpeedmentTool extends SpeedmentTaskBase {

  public static void main(String[] args)
  {
    if (args.length == 0)
    {
      new SpeedmentTool().executeWithoutConfig();
    }
    else
    {
      new SpeedmentTool().execute(new File(args[0]));
    }
  }

  public void executeWithoutConfig()
  {
    final Speedment speedment = buildSpeedment(Optional.empty());

    final Injector injector = speedment.getOrThrow(Injector.class);
    MainApp.setInjector(injector);

    Application.launch(MainApp.class);
  }

  @Override
  public void execute(File config)
  {

    final Speedment speedment = buildSpeedment(Optional.of(config));

    final Injector injector = speedment.getOrThrow(Injector.class);
    MainApp.setInjector(injector);

    Application.launch(MainApp.class, config.getAbsolutePath());
  }


}
