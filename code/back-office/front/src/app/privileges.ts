export class Privileges {
  public static readonly ADMIN = 'ADMIN';
  public static readonly OPERATOR = 'OPERATOR';
  public static readonly CCID = 'CCID';

  public static readonly ALL = [this.ADMIN, this.OPERATOR, this.CCID];
}
