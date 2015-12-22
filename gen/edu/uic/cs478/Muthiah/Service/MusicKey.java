/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: F:\\EclipseWorkspace\\AudioServer\\src\\edu\\uic\\cs478\\Muthiah\\Service\\MusicKey.aidl
 */
package edu.uic.cs478.Muthiah.Service;
public interface MusicKey extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements edu.uic.cs478.Muthiah.Service.MusicKey
{
private static final java.lang.String DESCRIPTOR = "edu.uic.cs478.Muthiah.Service.MusicKey";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an edu.uic.cs478.Muthiah.Service.MusicKey interface,
 * generating a proxy if needed.
 */
public static edu.uic.cs478.Muthiah.Service.MusicKey asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof edu.uic.cs478.Muthiah.Service.MusicKey))) {
return ((edu.uic.cs478.Muthiah.Service.MusicKey)iin);
}
return new edu.uic.cs478.Muthiah.Service.MusicKey.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_startMusic:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.startMusic(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_stopMusic:
{
data.enforceInterface(DESCRIPTOR);
this.stopMusic();
reply.writeNoException();
return true;
}
case TRANSACTION_pauseMusic:
{
data.enforceInterface(DESCRIPTOR);
this.pauseMusic();
reply.writeNoException();
return true;
}
case TRANSACTION_resumePlay:
{
data.enforceInterface(DESCRIPTOR);
this.resumePlay();
reply.writeNoException();
return true;
}
case TRANSACTION_readTransactionData:
{
data.enforceInterface(DESCRIPTOR);
java.util.List<java.lang.String> _result = this.readTransactionData();
reply.writeNoException();
reply.writeStringList(_result);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements edu.uic.cs478.Muthiah.Service.MusicKey
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
@Override public void startMusic(int clipID) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(clipID);
mRemote.transact(Stub.TRANSACTION_startMusic, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void stopMusic() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_stopMusic, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void pauseMusic() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_pauseMusic, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void resumePlay() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_resumePlay, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public java.util.List<java.lang.String> readTransactionData() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.util.List<java.lang.String> _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_readTransactionData, _data, _reply, 0);
_reply.readException();
_result = _reply.createStringArrayList();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_startMusic = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_stopMusic = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_pauseMusic = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_resumePlay = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_readTransactionData = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
}
public void startMusic(int clipID) throws android.os.RemoteException;
public void stopMusic() throws android.os.RemoteException;
public void pauseMusic() throws android.os.RemoteException;
public void resumePlay() throws android.os.RemoteException;
public java.util.List<java.lang.String> readTransactionData() throws android.os.RemoteException;
}
