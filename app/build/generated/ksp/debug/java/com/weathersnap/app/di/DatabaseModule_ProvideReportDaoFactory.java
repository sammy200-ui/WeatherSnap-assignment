package com.weathersnap.app.di;

import com.weathersnap.app.data.local.AppDatabase;
import com.weathersnap.app.data.local.ReportDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class DatabaseModule_ProvideReportDaoFactory implements Factory<ReportDao> {
  private final Provider<AppDatabase> databaseProvider;

  public DatabaseModule_ProvideReportDaoFactory(Provider<AppDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public ReportDao get() {
    return provideReportDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideReportDaoFactory create(
      Provider<AppDatabase> databaseProvider) {
    return new DatabaseModule_ProvideReportDaoFactory(databaseProvider);
  }

  public static ReportDao provideReportDao(AppDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideReportDao(database));
  }
}
