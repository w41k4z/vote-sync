export class Privileges {
  public static readonly ADMIN = 'ADMIN';
  public static readonly MANAGER = 'MANAGER';
  public static readonly OPERATOR = 'OPERATOR';

  public static readonly ALL = [this.ADMIN, this.MANAGER, this.OPERATOR];
}
