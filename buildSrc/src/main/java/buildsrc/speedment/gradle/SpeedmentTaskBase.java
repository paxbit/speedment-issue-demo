package buildsrc.speedment.gradle;

import com.speedment.generator.core.GeneratorBundle;
import com.speedment.generator.translator.internal.component.CodeGenerationComponentImpl;
import com.speedment.runtime.application.internal.DefaultApplicationBuilder;
import com.speedment.runtime.application.internal.DefaultApplicationMetadata;
import static com.speedment.runtime.application.internal.DefaultApplicationMetadata.METADATA_LOCATION;
import com.speedment.runtime.application.internal.EmptyApplicationMetadata;
import com.speedment.runtime.core.ApplicationBuilder;
import com.speedment.runtime.core.Speedment;
import com.speedment.tool.core.ToolBundle;
import com.speedment.tool.core.internal.component.UserInterfaceComponentImpl;
import java.io.File;
import java.util.Optional;

public abstract class SpeedmentTaskBase {


  public abstract void execute(File config);


  protected Speedment buildSpeedment(Optional<File> config)
  {
    final ApplicationBuilder<?, ?> result;

    // Configure config file location
    result = config
      .map(file ->
        new DefaultApplicationBuilder(DefaultApplicationMetadata.class)
          .withParam(METADATA_LOCATION, file.getAbsolutePath())
      ).orElseGet(() -> new DefaultApplicationBuilder(EmptyApplicationMetadata.class));

    result.withSkipCheckDatabaseConnectivity();

    // Add mandatory components that are not included in 'runtime'
    result
      .withSkipLogoPrintout()
      .withBundle(GeneratorBundle.class)
      .withBundle(ToolBundle.class)
      .withComponent(CodeGenerationComponentImpl.class)
      .withComponent(UserInterfaceComponentImpl.class);


    result.withSkipCheckDatabaseConnectivity();

    return result.build();
  }

}
