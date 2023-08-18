package dev.rgbmc.simpleinv.handlers;

import dev.rgbmc.simpleinv.objects.ClickState;
import java.util.function.Consumer;

public class ClickHandler {
  private final Consumer<ClickState> consumer;

  public ClickHandler(Consumer<ClickState> consumer) {
    this.consumer = consumer;
  }

  public void call(ClickState clickState) {
    consumer.accept(clickState);
  }
}
