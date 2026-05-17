package com.weathersnap.app.data.local;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class ReportDao_Impl implements ReportDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ReportEntity> __insertionAdapterOfReportEntity;

  public ReportDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfReportEntity = new EntityInsertionAdapter<ReportEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `reports` (`id`,`cityName`,`temperature`,`condition`,`humidity`,`windSpeed`,`pressure`,`notes`,`imagePath`,`originalImageSizeBytes`,`compressedImageSizeBytes`,`timestamp`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ReportEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getCityName());
        statement.bindDouble(3, entity.getTemperature());
        statement.bindString(4, entity.getCondition());
        statement.bindLong(5, entity.getHumidity());
        statement.bindDouble(6, entity.getWindSpeed());
        statement.bindDouble(7, entity.getPressure());
        statement.bindString(8, entity.getNotes());
        statement.bindString(9, entity.getImagePath());
        statement.bindLong(10, entity.getOriginalImageSizeBytes());
        statement.bindLong(11, entity.getCompressedImageSizeBytes());
        statement.bindLong(12, entity.getTimestamp());
      }
    };
  }

  @Override
  public Object insertReport(final ReportEntity report,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfReportEntity.insert(report);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<ReportEntity>> getAllReports() {
    final String _sql = "SELECT * FROM reports ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"reports"}, new Callable<List<ReportEntity>>() {
      @Override
      @NonNull
      public List<ReportEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCityName = CursorUtil.getColumnIndexOrThrow(_cursor, "cityName");
          final int _cursorIndexOfTemperature = CursorUtil.getColumnIndexOrThrow(_cursor, "temperature");
          final int _cursorIndexOfCondition = CursorUtil.getColumnIndexOrThrow(_cursor, "condition");
          final int _cursorIndexOfHumidity = CursorUtil.getColumnIndexOrThrow(_cursor, "humidity");
          final int _cursorIndexOfWindSpeed = CursorUtil.getColumnIndexOrThrow(_cursor, "windSpeed");
          final int _cursorIndexOfPressure = CursorUtil.getColumnIndexOrThrow(_cursor, "pressure");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfImagePath = CursorUtil.getColumnIndexOrThrow(_cursor, "imagePath");
          final int _cursorIndexOfOriginalImageSizeBytes = CursorUtil.getColumnIndexOrThrow(_cursor, "originalImageSizeBytes");
          final int _cursorIndexOfCompressedImageSizeBytes = CursorUtil.getColumnIndexOrThrow(_cursor, "compressedImageSizeBytes");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final List<ReportEntity> _result = new ArrayList<ReportEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ReportEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpCityName;
            _tmpCityName = _cursor.getString(_cursorIndexOfCityName);
            final double _tmpTemperature;
            _tmpTemperature = _cursor.getDouble(_cursorIndexOfTemperature);
            final String _tmpCondition;
            _tmpCondition = _cursor.getString(_cursorIndexOfCondition);
            final int _tmpHumidity;
            _tmpHumidity = _cursor.getInt(_cursorIndexOfHumidity);
            final double _tmpWindSpeed;
            _tmpWindSpeed = _cursor.getDouble(_cursorIndexOfWindSpeed);
            final double _tmpPressure;
            _tmpPressure = _cursor.getDouble(_cursorIndexOfPressure);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final String _tmpImagePath;
            _tmpImagePath = _cursor.getString(_cursorIndexOfImagePath);
            final long _tmpOriginalImageSizeBytes;
            _tmpOriginalImageSizeBytes = _cursor.getLong(_cursorIndexOfOriginalImageSizeBytes);
            final long _tmpCompressedImageSizeBytes;
            _tmpCompressedImageSizeBytes = _cursor.getLong(_cursorIndexOfCompressedImageSizeBytes);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            _item = new ReportEntity(_tmpId,_tmpCityName,_tmpTemperature,_tmpCondition,_tmpHumidity,_tmpWindSpeed,_tmpPressure,_tmpNotes,_tmpImagePath,_tmpOriginalImageSizeBytes,_tmpCompressedImageSizeBytes,_tmpTimestamp);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
