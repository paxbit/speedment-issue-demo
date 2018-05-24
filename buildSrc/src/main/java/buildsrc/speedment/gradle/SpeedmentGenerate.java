package buildsrc.speedment.gradle;

import com.speedment.generator.translator.TranslatorManager;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.core.Speedment;
import com.speedment.runtime.core.component.ProjectComponent;
import java.io.File;
import java.util.Optional;
import org.gradle.api.GradleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * todo comment.
 */
public class SpeedmentGenerate extends SpeedmentTaskBase {

  private static final Logger LOGGER = LoggerFactory.getLogger(SpeedmentGenerate.class);

  @Override
  public void execute(File config)
  {
    final Speedment speedment = buildSpeedment(Optional.of(config));

    LOGGER.info(
      "Generating code using JSON configuration file: '" + config.getAbsolutePath() + "'."
    );

    try
    {
      final Project project = speedment.getOrThrow(ProjectComponent.class).getProject();
      speedment.getOrThrow(TranslatorManager.class).accept(project);
    }
    catch (Exception ex)
    {
      throw new GradleException("Error parsing configFile file.", ex);
    }
  }
}
