package com.weathersnap.app.ui.viewmodel;

import com.weathersnap.app.data.remote.OpenMeteoApi;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class WeatherViewModel_Factory implements Factory<WeatherViewModel> {
  private final Provider<OpenMeteoApi> apiProvider;

  public WeatherViewModel_Factory(Provider<OpenMeteoApi> apiProvider) {
    this.apiProvider = apiProvider;
  }

  @Override
  public WeatherViewModel get() {
    return newInstance(apiProvider.get());
  }

  public static WeatherViewModel_Factory create(Provider<OpenMeteoApi> apiProvider) {
    return new WeatherViewModel_Factory(apiProvider);
  }

  public static WeatherViewModel newInstance(OpenMeteoApi api) {
    return new WeatherViewModel(api);
  }
}
